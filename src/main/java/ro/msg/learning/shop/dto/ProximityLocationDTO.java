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


    public static ProximityLocationDTO of(Address address){

        return ProximityLocationDTO.builder()
                .cityState(address.getCity()+","+" "+address.getState())
                .build();
    }
}
