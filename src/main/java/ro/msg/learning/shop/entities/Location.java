package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToOne
    @JoinColumn(name = "address")
    private Address address;
    /*String addressCountry;
    String addressCity;
    String addressCounty;
    String addressStreetAddress;*/

    @OneToMany(mappedBy="location")
    Set<Stock> stock;
}

