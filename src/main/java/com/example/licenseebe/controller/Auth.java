package com.example.licenseebe.controller;

import com.example.licenseebe.dto.request.CreateNewUserDTO;
import com.example.licenseebe.dto.request.LoginUserDTO;
import com.example.licenseebe.dto.request.ResetUserPasswordDto;
import com.example.licenseebe.dto.request.VerifyTokenRequestDto;
import com.example.licenseebe.helper.HttpStatusHelper;
import com.example.licenseebe.service.AuthService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import static com.example.licenseebe.helper.HttpStatusHelper.success;

@RestController
@Log4j2
@CrossOrigin
@RequestMapping("/auth")
public class Auth {

    private final AuthService authService;
    private final HttpStatusHelper httpStatusHelper;

    @Autowired
    public Auth(AuthService auth, HttpStatusHelper http) {
        this.authService = auth;
        this.httpStatusHelper = http;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/register")
    public ResponseEntity<Object> registerIntoApp(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody CreateNewUserDTO newUser) {
        try {
            return success("response", authService.registerNewUser(httpServletRequest, httpServletResponse, newUser));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, newUser);
        }

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "USER_NOT_FOUND"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/login")
    public ResponseEntity<Object> loginIntoApp(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody LoginUserDTO user) {
        try {
            return success("response", authService.loginExistentUser(httpServletRequest, httpServletResponse, user));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, user);
        }

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 413, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("/verify")
    public ResponseEntity<Object> verify(HttpServletRequest httpServletRequest, @RequestBody VerifyTokenRequestDto verifyTokenRequestDto) {
        try {
            return success("response", authService.verify(verifyTokenRequestDto, httpServletRequest));

        } catch (Exception e) {
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, verifyTokenRequestDto);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 413, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("/refresh-access-token")
    public ResponseEntity<Object> refreshAccessToken(HttpServletRequest httpServletRequest, @RequestParam String refreshToken) {
        try {
            return success("response", authService.refreshAccessToken(refreshToken, httpServletRequest));

        } catch (Exception e) {
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, refreshToken);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 413, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("/change-forgotten-password")
    public ResponseEntity<Object> changeForgottenPassword(HttpServletRequest httpServletRequest, @RequestBody ResetUserPasswordDto resetUserPasswordDto ) {
        try {
            return success("response", authService.resetUserPassword(resetUserPasswordDto));

        } catch (Exception e) {
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, resetUserPasswordDto);
        }
    }
}
