package edu.miu.cs544.service;

import edu.miu.cs544.security.jwt.JwtUtils;
import edu.miu.cs544.security.service.JwtUserDetails;
import edu.miu.cs544.service.response.UserResponse;
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

    public UserResponse validateToken(String token) {

        String email;
        UserResponse userResponse = new UserResponse();

        try {
            email = jwtUtils.getUsernameFromToken(token);
            if (email != null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
                if (jwtUtils.validateToken(token, userDetails)) {
                    return constructTokenResponse((JwtUserDetails)userDetails, userResponse);
                }
            }
        } catch (Exception e) {
            userResponse.setValid(false);
        }

        return userResponse;
    }

    private UserResponse constructTokenResponse(JwtUserDetails userDetails, UserResponse userResponse) {
        userResponse.setId(userDetails.getId());
        userResponse.setValid(true);
        userResponse.setEmail(userDetails.getUsername());
        userResponse.setRole(userDetails.getRole());
        userResponse.setPassengerId(userDetails.getPassengerId());

        return userResponse;
    }
}
