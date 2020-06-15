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

    private DateFormat dateFormat = new SimpleDateFormat("MM dd, yyyy");

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        Role agentRole = new Role(ERole.ROLE_AGENT);
        Role passengerRole = new Role(ERole.ROLE_PASSENGER);;

        Address residenceAddress1 = new Address("1000N 4th str","Fairfield","IA","52557");
        Address residenceAddress2 = new Address("1000N 3th str","Chicago","IL","52556");
        Address residenceAddress3 = new Address("1000N 2th str","Chicago","IL","52555");
        Address residenceAddress4 = new Address("1000N 1th str","Chicago","IL","52554");

        User admin = new Admin("admin", "admin", dateFormat.parse("08 07, 1987"), "admin@miu.edu", encodePassword("admin"));
        User agent1 = new Agent("agent", "agent", dateFormat.parse("08 07, 1987"), "agent@miu.edu", encodePassword("agent"));
        User passenger1 = new Passenger("Otgonbayar", "Otgonbayar", dateFormat.parse("08 07, 1987"), "pass1@miu.edu", encodePassword("pass"), residenceAddress1);
        User passenger2 = new Passenger("Nahom", "Berta", dateFormat.parse("08 07, 1987"), "pass2@miu.edu", encodePassword("pass"), residenceAddress2);
        User passenger3 = new Passenger("Yodit", "Wondaferew ", dateFormat.parse("08 07, 1987"), "pass3@miu.edu", encodePassword("pass"), residenceAddress3);
        User passenger4 = new Passenger("Thi Le", "Nguyen", dateFormat.parse("08 07, 1987"), "pass4@miu.edu", encodePassword("pass"), residenceAddress4);

        admin.setRole(adminRole);
        agent1.setRole(agentRole);
        passenger1.setRole(passengerRole);
        passenger2.setRole(passengerRole);
        passenger3.setRole(passengerRole);
        passenger4.setRole(passengerRole);

        userRepository.saveAll(Arrays.asList(passenger1, passenger2, passenger3, passenger4, admin, agent1));
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);

    }
}
