package com.example.licenseebe.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.licenseebe.model.User;
import com.example.licenseebe.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TokenService {

    UserRepository userRepository;


    @Autowired
    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, String> generateTokens(String email, UserRole role, HttpServletRequest request) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String accessToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", String.valueOf(role))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    public static String getEmailFromToken(String accessToken) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT;
        decodedJWT = verifier.verify(accessToken);
        return decodedJWT.getSubject();
    }

    public boolean isUserRoleAdmin(String accessToken) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT;
        decodedJWT = verifier.verify(accessToken);
        String role = decodedJWT.getClaim("role").asString();
        return (role.equals("Admin"));
    }

    public String refreshAccessToken(String refreshToken, HttpServletRequest request) throws CustomConflictException {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = JWT.decode(refreshToken);
        if (decodedJWT.getExpiresAt().before(new Date()))
            throw new CustomConflictException(Conflict.REFRESH_TOKEN_INVALID);
        decodedJWT = verifier.verify(refreshToken);
        String email = decodedJWT.getSubject();
        Optional<User> optionalUser = userRepository.getUserByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            String accessToken = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("role", String.valueOf(user.getRole()))
                    .sign(algorithm);
            return accessToken;
        }
        throw new CustomConflictException(Conflict.REFRESH_TOKEN_INVALID);
    }

    public boolean isTokenExpiredNotStatic(String token) throws CustomConflictException {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            if (decodedJWT.getExpiresAt().before(new Date())) {
                return true;
            } else
                return false;
        } catch (Exception e) {
            throw new CustomConflictException(Conflict.ACCESS_TOKEN_INVALID);
        }
    }

    public static boolean isTokenExpired(String token) throws CustomConflictException {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            if (decodedJWT.getExpiresAt().before(new Date())) {
                return true;
            } else
                return false;
        } catch (Exception e) {
            throw new CustomConflictException(Conflict.ACCESS_TOKEN_INVALID);
        }
    }

    public User checkSession(String accessToken) throws CustomConflictException {
        if (isTokenExpired(accessToken))
            throw new CustomConflictException(Conflict.ACCESS_TOKEN_HAS_EXPIERED);
        String email = getEmailFromToken(accessToken);
        Optional<User> optionalUser = userRepository.getUserByEmail(email);
        if (optionalUser.isEmpty())
            throw new CustomConflictException(Conflict.USER_NOT_FOUND);
        return optionalUser.get();
    }
}