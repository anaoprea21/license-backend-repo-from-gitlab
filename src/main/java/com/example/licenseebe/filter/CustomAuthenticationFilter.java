package com.example.licenseebe.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.licenseebe.helper.Conflict;
import com.example.licenseebe.helper.CustomConflictException;
import com.example.licenseebe.helper.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@WebFilter(urlPatterns = "/*")
@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;
    private List<String> allowedURLs = Collections.unmodifiableList(Arrays.asList("/auth/login", "/auth/refresh-access-token"
            , "/auth/verify", "/auth/register", "/user/send-forgot-password-email", "/auth/change-forgotten-password"));
    private List<String> adminURLs = Collections.unmodifiableList(Arrays.asList("/book/edit-book","/book/add-book","/book/delete-book","/book/get-all-admin-books"));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (allowedURLs.contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
        } else {
            String accessToken = request.getHeader("Accesstoken");
            try {
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(accessToken);
                if (adminURLs.contains(request.getServletPath())) {
                    boolean isAdmin = tokenService.isUserRoleAdmin(accessToken);
                    if (isAdmin) {
                        filterChain.doFilter(request, response);
                    } else {
                        throw new CustomConflictException(Conflict.USER_NOT_ADMIN);
                    }
                } else
                    filterChain.doFilter(request, response);
            } catch (Exception e) {
                if (e.getMessage().equals("USER_NOT_ADMIN")) {
                    response.setHeader("error", e.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", "USER_NOT_ADMIN");
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }

                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", "INVALID_TOKEN");
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        }
    }
}