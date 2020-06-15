package edu.miu.cs544.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("agent")
public class Agent extends User {

    public Agent() {
    }

    public Agent(String firstName, String lastName, Date dateOfBirth, String email, String password) {
        super(firstName, lastName, dateOfBirth, email, password);
    }

}