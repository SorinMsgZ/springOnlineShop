package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String emailAddress;
    private String token;
}
