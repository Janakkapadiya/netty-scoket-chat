package com.broadcastsocket.web.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final String AUTH_HEADER_PREFIX = "Bearer ";
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    @Qualifier("userDetails")
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = null;
        String username = null;
        if (authorizationHeader != null && authorizationHeader.startsWith(AUTH_HEADER_PREFIX)) {
            jwtToken = authorizationHeader.substring(AUTH_HEADER_PREFIX.length());
            try {
                username = jwtUtil.getUserNameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.info("Unable to get Jwt token");
            } catch (ExpiredJwtException e) {
                logger.info("Jwt token is expired");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                }
            }
        }
        filterChain.doFilter(request, response);
    }
}


