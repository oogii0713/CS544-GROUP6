package edu.miu.cs544.service;

import edu.miu.cs544.security.jwt.JwtUtils;
import edu.miu.cs544.security.service.JwtUserDetails;
import edu.miu.cs544.service.response.TokenValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    @Qualifier("JwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    JwtUtils jwtUtils;

    public TokenValidationResponse validateToken(String token) {

        String email = null;
        TokenValidationResponse tokenValidationResponse = new TokenValidationResponse();

        try {
            email = jwtUtils.getUsernameFromToken(token);
            if (email != null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
                if (jwtUtils.validateToken(token, userDetails)) {
                    return constructTokenResponse((JwtUserDetails)userDetails, tokenValidationResponse);
                }
            }
        } catch (Exception e) {
            tokenValidationResponse.setValid(false);

        }
        return tokenValidationResponse;
    }

    private TokenValidationResponse constructTokenResponse(JwtUserDetails userDetails, TokenValidationResponse tokenValidationResponse) {
        tokenValidationResponse.setValid(true);
        tokenValidationResponse.setUsername(userDetails.getUsername());

        tokenValidationResponse.setRole(userDetails.getRole());
        return tokenValidationResponse;
    }
}
