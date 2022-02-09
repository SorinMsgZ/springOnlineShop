package ro.msg.learning.shop.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@Document
public class ProductMongoDb implements Serializable {
    @Id
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private double weight;
    private String category;
    private String supplier;
    private String imageUrl;

}



