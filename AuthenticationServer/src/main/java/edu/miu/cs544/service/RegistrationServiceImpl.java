package edu.miu.cs544.service;

import edu.miu.cs544.domain.*;
import edu.miu.cs544.exception.EmailAlreadyExistException;
import edu.miu.cs544.repository.RoleRepository;
import edu.miu.cs544.repository.UserRepository;
import edu.miu.cs544.service.request.UserRequest;
import edu.miu.cs544.service.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Override
    public UserResponse saveUser(UserRequest userRequest, ERole roleType) throws EmailAlreadyExistException {
        isUserNameExists(userRequest.getEmail());

        User user;
        if (roleType.equals(ERole.ROLE_PASSENGER)) {
            user = new User(userRequest.getEmail(), encodePassword(userRequest.getPassword()));
            user.setPassengerId(userRequest.getPassengerId());
        } else {
            user = new User(userRequest.getEmail(), encodePassword(userRequest.getPassword()));
        }

        Role role = roleRepository.findByName(roleType).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRole(role);
        userRepository.save(user);

        return new UserResponse(user.getId(), user.getUsername(), role.getName().toString().substring(5), user.getPassengerId(), true);

    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);

    }

    private void isUserNameExists(String username) throws EmailAlreadyExistException {
        if (userRepository.existsByUsername(username))
            throw new EmailAlreadyExistException("Email is already registered!");
    }

}
