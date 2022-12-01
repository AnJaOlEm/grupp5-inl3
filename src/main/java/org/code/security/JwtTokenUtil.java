package org.code.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.code.data.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.util.Calendar;
import java.util.Date;


@Component
public class JwtTokenUtil {

    private final String jwtSecret = "CctlD5JL16m8wLTgsFNhzqjQP";
    private final String jwtIssuer = "GRUPP5";

    public String generateAccessToken(User user) {
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret.getBytes());


        return JWT.create()
                .withSubject( user.getUsername())
                .withIssuer(jwtIssuer)
                //.withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .sign(algorithm);
    }

//    public String generateRefreshToken(User user) {
//        Algorithm algorithm = Algorithm.HMAC512(jwtSecret.getBytes());
//
//        return JWT.create()
//                .withSubject( user.getUsername())
//                .withIssuer(jwtIssuer)
//                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
//                .sign(algorithm);
//    }

    public boolean validate(String token, UserDetails userDetails) {
//        try {
//            Long expiresAt = JWT.decode(token).getExpiresAt().getTime();
//            Calendar cal = Calendar.getInstance();
//            if (expiresAt >= cal.getTime().getTime()) {
//                return true;
//            }
//        } catch (IllegalArgumentException e) {
//            System.out.println(String.format("JWT is invalid - {%s}", e.getMessage()));
//        }
//
//        return false;


            final String username = getUserName(token);
            return (username.equals(userDetails.getUsername()));


    }

    public String getUserName(String token) {
        String subject = JWT.decode(token).getSubject();

        return subject;
    }

}
