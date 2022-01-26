package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.Address;

@Data
@Builder
@AllArgsConstructor
public class ProximityLocationDTO {
    private String cityState;


    public static ProximityLocationDTO of(Address address) {
        StringBuilder resultFormat = new StringBuilder();
        resultFormat.append(address.getCity());
        resultFormat.append(",");
        resultFormat.append(" ");
        resultFormat.append(address.getState());

        return ProximityLocationDTO.builder()
                .cityState(resultFormat.toString())
                .build();
    }
}
