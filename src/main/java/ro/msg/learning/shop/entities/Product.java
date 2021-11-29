package ro.msg.learning.shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@Entity

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private double weight;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category")
    private ProductCategory category;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier")
    private Supplier supplier;
    private String imageUrl;


}



