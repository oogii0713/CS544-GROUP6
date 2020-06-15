package edu.miu.cs544.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Date;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(String firstName, String lastName, Date dateOfBirth, String email, String password) {
        super(firstName, lastName, dateOfBirth, email, password);
    }

}
