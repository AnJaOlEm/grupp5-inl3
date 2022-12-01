package org.code.controller;

import org.code.model.LoginDto;
import org.code.model.RegistrationDto;
import org.code.data.User;
import org.code.security.JwtTokenUtil;
import org.code.security.TokenProvider;
import org.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        Authentication authentication = authenticationManager.authenticate(token);

        org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());

        String accessToken = tokenProvider.generate(user);
        //String refreshToken = jwtTokenUtil.generateRefreshToken(user);

//        Map<String, String> tokens = new HashMap<>();
//        tokens.put("access_token", accessToken);
        //tokens.put("refresh_token", refreshToken);

        return ResponseEntity.ok().body(accessToken.trim());
    }

//    @PostMapping("refresh")
//    public ResponseEntity< String> refresh(HttpServletRequest request) {
//        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String refreshToken = authorizationHeader.substring("Bearer ".length());
//            if (jwtTokenUtil.validate(refreshToken)) {
//                org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) userService.loadUserByUsername(jwtTokenUtil.getUserName(refreshToken));
//                User user = userService.getByUsername(userDetails.getUsername());
//
//                String accessToken = jwtTokenUtil.generateAccessToken(user);
//
//                Map<String, String> tokens = new HashMap<>();
//                tokens.put("access_token", accessToken);
//
//                return ResponseEntity.ok().body(tokens);
//            }

//            org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) userService.loadUserByUsername(jwtTokenUtil.getUserName(refreshToken));
//            User user = userService.getByUsername(userDetails.getUsername());
//            String token = jwtTokenUtil.generateAccessToken(user);

//            return ResponseEntity.ok().body(token);
//        }
//
//        return ResponseEntity.badRequest().body(null);
//    }

    @PostMapping("register")
    public User register(@RequestBody RegistrationDto request) {
        return userService.create(request);
    }
    @GetMapping("test")
    public String test(){
        return "Tessst";
    }

}
