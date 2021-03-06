package ro.msg.learning.shop.entities;

import lombok.Data;


import javax.persistence.*;

@Data
@Entity
@Table(name="User_Customer")
public class User {
    @Id
    private String username;
    private String password;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

}
