package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "shippedFrom")
    private Location shippedFrom;
    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;
    private LocalDateTime createdAt;
    @OneToOne
    @JoinColumn(name = "address")
    private Address address;
    /*    String addressCountry;
        String addressCity;
        String addressCounty;
        String addressStreetAddress;*/

    @OneToMany(mappedBy = "orders")
    Set<OrderDetail> orderDetail;
}

