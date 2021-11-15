package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private  String name;
    @OneToOne
    @JoinColumn(name = "id")
    private  Address address;
    /*String addressCountry;
    String addressCity;
    String addressCounty;
    String addressStreetAddress;*/
}
