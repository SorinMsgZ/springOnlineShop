package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String name;
    private  String description;
    private  BigDecimal price;
    private  double weight;
    @ManyToOne
    @JoinColumn(name = "id")
    private  ProductCategory category;
    @ManyToOne
    @JoinColumn(name = "id")
    private  Supplier supplier;
    private  String imageUrl;
}
