package ro.msg.learning.shop.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "location")
    private Location location;
    private Date localDate;
    private  BigDecimal sum;
}
