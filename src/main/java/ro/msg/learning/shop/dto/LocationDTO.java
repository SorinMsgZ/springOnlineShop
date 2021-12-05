package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Location;

@Data
@Builder
public class LocationDTO {
    private String name;
    private Address address;

    public LocationDTO(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Location toEntity() {
        Location result = new Location();
        result.setName(name);
        result.setAddress(address);
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Location location) {
        location.setName(name);
        location.setAddress(address);
    }

    public static LocationDTO of(Location entity) {
        return LocationDTO.builder()
                .name(entity.getName())
                .address(entity.getAddress())
                .build();
    }
}


