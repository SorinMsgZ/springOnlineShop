package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Revenue;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
public class RevenueDTO {
    private Location location;
    private LocalDate localDate;
    private BigDecimal sum;

    public RevenueDTO(Location location, LocalDate localDate, BigDecimal sum) {
        this.location = location;
        this.localDate = localDate;
        this.sum = sum;
    }

    public Revenue toEntity() {
        Revenue result = new Revenue();
        result.setLocation(location);
        result.setLocalDate(localDate);
        result.setSum(sum);
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Revenue revenue) {
        revenue.setLocation(location);
        revenue.setLocalDate(localDate);
        revenue.setSum(sum);

    }

    public static RevenueDTO of(Revenue entity) {
        return RevenueDTO.builder()
                .location(entity.getLocation())
                .localDate(entity.getLocalDate())
                .sum(entity.getSum())
                .build();
    }
}
