package ro.msg.learning.shop.entities;

import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name="User_Customer")
public class User {
    @Id
    @NotBlank(message = "Name is mandatory")
    private String username;
    @NotBlank(message = "Name is mandatory")
    private String password;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

}
