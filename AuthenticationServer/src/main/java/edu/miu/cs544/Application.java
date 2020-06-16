package edu.miu.cs544;

import edu.miu.cs544.domain.*;
import edu.miu.cs544.repository.RoleRepository;
import edu.miu.cs544.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        Role agentRole = new Role(ERole.ROLE_AGENT);
        Role passengerRole = new Role(ERole.ROLE_PASSENGER);

        User admin = new User("admin@miu.edu", encodePassword("admin"));
        User agent1 = new User("agent@miu.edu", encodePassword("agent"));
        User passenger1 = new User("pass1@miu.edu", encodePassword("pass"));
        User passenger2 = new User("pass2@miu.edu", encodePassword("pass"));
        User passenger3 = new User("pass3@miu.edu", encodePassword("pass"));
        User passenger4 = new User("pass4@miu.edu", encodePassword("pass"));

        admin.setRole(adminRole);
        agent1.setRole(agentRole);
        passenger1.setRole(passengerRole);
        passenger2.setRole(passengerRole);
        passenger3.setRole(passengerRole);
        passenger4.setRole(passengerRole);
        passenger1.setPassengerId(10000);
        passenger2.setPassengerId(10001);
        passenger3.setPassengerId(10002);
        passenger4.setPassengerId(10003);

        userRepository.saveAll(Arrays.asList(passenger1, passenger2, passenger3, passenger4, admin, agent1));
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);

    }
}
