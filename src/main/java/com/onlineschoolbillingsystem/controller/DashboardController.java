package com.onlineschoolbillingsystem.controller;

import com.onlineschoolbillingsystem.request.AuthRequest;
import com.onlineschoolbillingsystem.service.InstituteService;
import com.onlineschoolbillingsystem.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class which contains all the
 * dashboard related endpoints.
 */
@RestController
public class DashboardController {

    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    @Autowired
    public DashboardController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, InstituteService instituteService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/")
    public String welcome() {
        return "Welcome to the dashboard";
    }

    /**
     * Endpoint to generate an auth token.
     *
     * @param authRequest - request details username/pass.
     * @return - token.
     */
    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    /*
                     * To validate username and password
                     * before generate a token for the authentication.
                     */
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception exception) {
            // throw new Exception("Invalid credentials");
            return "Invalid credentials";
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }
}
