package ro.msg.learning.shop.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String country;
    private String city;
    private String county;
    private String streetAddress;
    private String state;

    public Address(int id, String country, String city, String county, String streetAddress, String state) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.county = county;
        this.streetAddress = streetAddress;
        this.state = state;
    }
}
