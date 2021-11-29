package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Supplier;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Builder
public class LocationDTO {
    private int id;
    private String name;
    private Address address;

    public Location toEntity() {
        Location result = new Location();
        result.setId(id);
        result.setName(name);
        result.setAddress(address);
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Location location) {
        location.setId(id);
        location.setName(name);
        location.setAddress(address);
    }

    public static LocationDTO of(Location entity) {
        return LocationDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .build();
    }
}


