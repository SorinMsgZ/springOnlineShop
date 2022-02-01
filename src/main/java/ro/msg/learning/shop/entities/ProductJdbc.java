package ro.msg.learning.shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductJdbc implements Serializable {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private double weight;
    private int category;
    private int supplier;
    private String imageUrl;
}

