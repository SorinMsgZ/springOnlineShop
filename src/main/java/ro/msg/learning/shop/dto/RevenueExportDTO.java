package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import ro.msg.learning.shop.entities.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder

@AllArgsConstructor
@JsonPropertyOrder({"locationId", "localDateString", "sumInt"})
@Getter
@ToString
@EqualsAndHashCode
public class RevenueExportDTO implements Serializable {
    @JsonProperty("location")
    private int locationId;
    @JsonProperty("localDate")
    private String localDateString;
    @JsonProperty("sum")
    private int sumInt;

    public RevenueExportDTO() {
    }

    public RevenueExportDTO(Revenue revenue) {
        this.locationId = revenue.getLocation().getId();
        this.localDateString = revenue.getLocalDate().toString();
        this.sumInt = revenue.getSum().intValue();
    }

    public Revenue toEntity() {
        Revenue result = new Revenue();
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Revenue revenue) {
        Location location = new Location();
        location.setId(locationId);
        LocalDate localDate = LocalDate.parse(localDateString);
        BigDecimal sum = new BigDecimal(sumInt);

        revenue.setLocation(location);
        revenue.setLocalDate(localDate);
        revenue.setSum(sum);
    }

    public static RevenueExportDTO of(Revenue entity) {
        return RevenueExportDTO.builder()
                .locationId(entity.getId())
                .localDateString(entity.getLocalDate().toString())
                .sumInt(entity.getSum().intValue())
                .build();
    }
}