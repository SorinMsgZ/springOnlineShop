package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity

public class Supplier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
