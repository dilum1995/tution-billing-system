package com.onlineschoolbillingsystem.filter;

import com.onlineschoolbillingsystem.service.InstituteService;
import com.onlineschoolbillingsystem.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * To authenticate and validate token
 * once per every request.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    //To get the extraction logic for a token.
    private JwtUtil jwtUtil;

    private InstituteService instituteService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, InstituteService instituteService) {
        this.jwtUtil = jwtUtil;
        this.instituteService = instituteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        String username = null;
        //Namespace - Bearer and token.
        //Validate header.
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            //Extracting the token information.
            token = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }
        //Validate username and security context holder.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = instituteService.loadUserByUsername(username);
            //Validate token.
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
