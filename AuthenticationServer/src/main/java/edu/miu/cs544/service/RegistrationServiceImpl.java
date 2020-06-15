package edu.miu.cs544.service;

import edu.miu.cs544.domain.*;
import edu.miu.cs544.exception.EmailAlreadyExistException;
import edu.miu.cs544.repository.RoleRepository;
import edu.miu.cs544.repository.UserRepository;
import edu.miu.cs544.service.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    

    @Transactional
    public void saveUser(SignupRequest signupRequest, ERole roleType) throws EmailAlreadyExistException {

        isEmailExist(signupRequest.getEmail());

        User user = null;
        switch (roleType) {
            case ROLE_PASSENGER:
                Address address = new Address(signupRequest.getStreet(), signupRequest.getCity(), signupRequest.getState(), signupRequest.getZip());
                user = new Passenger(signupRequest.getFirstName(), signupRequest.getLastName(), signupRequest.getDateOfBirth(), signupRequest.getEmail(), encodePassword(signupRequest.getPassword()), address);
                break;
            case ROLE_AGENT:
                user = new Agent(signupRequest.getFirstName(), signupRequest.getLastName(), signupRequest.getDateOfBirth(), signupRequest.getEmail(), encodePassword(signupRequest.getPassword()));
                break;

        }

        Role role = roleRepository.findByName(roleType).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRole(role);

        userRepository.save(user);

    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);

    }

    private void isEmailExist(String email) throws EmailAlreadyExistException {
        if (userRepository.existsByEmail(email))
            throw new EmailAlreadyExistException("Error: Email is already in use!");
    }

}
