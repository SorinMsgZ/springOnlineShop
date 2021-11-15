package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @ManyToOne
    @JoinColumn(name = "id")
    private  Location shippedFrom;
    @ManyToOne
    @JoinColumn(name = "id")
    private  Customer customer;
    private  LocalDateTime createdAt;
    @OneToOne
    @JoinColumn(name = "id")
    private  Address address;
   /* String addressCountry;
    String addressCity;
    String addressCounty;
    String addressStreetAddress;*/
}
