package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name="Product_Category")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String description;
}
