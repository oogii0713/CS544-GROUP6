package edu.miu.cs544.domain;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@DiscriminatorValue("passenger")
public class Passenger extends User{
    @OneToOne(cascade = {CascadeType.ALL})
    private Address address;

    public Passenger() {
        super();
    }

    public Passenger(String firstName, String lastName, Date dateOfBirth, String email, String password, Address address) {
        super(firstName, lastName, dateOfBirth, email, password);
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
