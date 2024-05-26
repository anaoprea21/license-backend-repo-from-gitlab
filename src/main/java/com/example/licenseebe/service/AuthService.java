package com.example.licenseebe.service;

import com.example.licenseebe.controller.EmailController;
import com.example.licenseebe.dto.request.CreateNewUserDTO;
import com.example.licenseebe.dto.request.LoginUserDTO;
import com.example.licenseebe.dto.request.ResetUserPasswordDto;
import com.example.licenseebe.dto.request.VerifyTokenRequestDto;
import com.example.licenseebe.helper.*;
import com.example.licenseebe.model.User;
import com.example.licenseebe.model.User2FACode;
import com.example.licenseebe.repository.user.CodeRepository;
import com.example.licenseebe.repository.user.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {
    @Value("${license.google.token.id}")
    private String googleTokenId;

    final UserRepository userRepository;

    final EmailController emailController;

    final
    CodeRepository codeRepository;

    final
    TokenService tokenService;

    private CustomValidators custom = new CustomValidators();


    @Autowired
    public AuthService(UserRepository userRepository, CodeRepository codeRepository, EmailController emailController, TokenService tokenService) {
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
        this.emailController = emailController;
        this.tokenService = tokenService;
    }

    public Object registerNewUser(HttpServletRequest request, HttpServletResponse response, CreateNewUserDTO newUser) throws CustomConflictException {

        if (custom.isEmailValid(newUser.getEmail())) {
            Optional<User> optionalUser = userRepository.getUserByEmail(newUser.getEmail());
            if (optionalUser.isPresent()) {
                throw new CustomConflictException(Conflict.USER_ALREADY_EXISTS);
            }
            Optional<User> optionalUser2 = userRepository.getUserByUsername(newUser.getUsername());
            if (optionalUser2.isPresent()) {
                throw new CustomConflictException(Conflict.USERNAME_ALREADY_EXISTS);
            }
            User newUserData = User.builder()
                    .role(newUser.getRole())
                    .email(newUser.getEmail())
                    .username(newUser.getUsername())
                    .password(newUser.getPassword())
                    .build();
            userRepository.save(newUserData);
            return new ResponseEntity<>(Response.USER_REGISTERED_SUCCESSFULLY, HttpStatus.OK).getBody();

        } else {
            throw new CustomConflictException(Conflict.USER_NOT_VALID);
        }
    }

    public Object loginExistentUser(HttpServletRequest request, HttpServletResponse response, LoginUserDTO user) throws CustomConflictException, IOException {

        if (custom.isEmailValid(user.getEmail())) {
            Optional<User> optionalUser = userRepository.getUserByEmail(user.getEmail());
            if (optionalUser.isPresent()) {
                User loggedInUser = optionalUser.get();
                if (user.getPassword().equals(loggedInUser.getPassword())) {
                    return checkAndSend2FAEmail(loggedInUser, request);
                } else {
                    throw new CustomConflictException(Conflict.INCORRECT_PASSWORD);
                }
            } else {
                throw new CustomConflictException(Conflict.USER_NOT_FOUND);
            }
        } else {
            throw new CustomConflictException(Conflict.EMAIL_NOT_VALID);
        }
    }

    public Object checkAndSend2FAEmail(User user, HttpServletRequest request) throws IOException {
        if (user.isTwoFactorStatus()) {
            String[] emailsArray = new String[1];
            emailsArray[0] = user.getEmail();
            Random randomGenerator = new Random();
            int number = randomGenerator.nextInt(999999);
            String generatedCode = String.format("%06d", number);
            Optional<User2FACode> optionalUser2FACode = codeRepository.getCodeByUserId(user.getId());
            if (optionalUser2FACode.isPresent()) {
                User2FACode user2FACode = optionalUser2FACode.get();
                user2FACode.setCode(generatedCode);
                codeRepository.save(user2FACode);
            } else {
                User2FACode user2FACode = new User2FACode();
                user2FACode.setCode(generatedCode);
                user2FACode.setUser(user);
                codeRepository.save(user2FACode);
            }
            emailController.sendSimpleEmail(EmailTemplates.sendVerificationEmail(generatedCode), "Two Factor Auth Code", emailsArray, null);

            return new HashMap<String, String>();
        } else {
            return tokenService.generateTokens(user.getEmail(), user.getRole(), request);
        }
    }

    public Object verify(VerifyTokenRequestDto verifyTokenRequestDto, HttpServletRequest request) throws CustomConflictException {
        Optional<User> optionalUser = userRepository.getUserByEmail(verifyTokenRequestDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isTwoFactorStatus()) {
                Optional<User2FACode> optionalUser2FACode = codeRepository.getCodeByUserId(user.getId());
                if (optionalUser2FACode.isPresent()) {
                    User2FACode user2FACode = optionalUser2FACode.get();
                    if (verifyTokenRequestDto.getCode().equals(user2FACode.getCode())) {
                        return tokenService.generateTokens(user.getEmail(), user.getRole(), request);
                    } else {
                        throw new CustomConflictException(Conflict.INVALID_CODE);
                    }
                } else {
                    throw new CustomConflictException(Conflict.CODE_NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(Response.LOG_IN_SUCCESSFUL, HttpStatus.OK).getBody();
            }
        } else {
            throw new CustomConflictException(Conflict.USER_NOT_FOUND);
        }
    }

    public Object refreshAccessToken(String refreshToken, HttpServletRequest httpServletRequest) throws CustomConflictException {
        return tokenService.refreshAccessToken(refreshToken, httpServletRequest);
    }

    public Object resetUserPassword(ResetUserPasswordDto resetUserPasswordDto) {
        Optional<User> optionalUser = userRepository.getUserByForgotPasswordCode(resetUserPasswordDto.getCode());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(resetUserPasswordDto.getPassword());
            userRepository.save(user);
        }
        return new ResponseEntity<>(Response.PASSWORD_CHANGED_SUCCESSFULLY, HttpStatus.OK).getBody();

    }
}
