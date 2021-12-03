package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Location;

@Data
@Builder
public class LocationDTO {
    private int id;
    private String name;
    private Address address;

    //TODO check comments
    public LocationDTO(int id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Location toEntity() {
        Address addressX =
                new Address(address.getId(), address.getCountry(), address.getCity(), address.getCounty(), address
                        .getStreetAddress());
        Location result = new Location(id, name, addressX);
//        result.setId(id);
//        result.setName(name);
//        result.setAddress(address);
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Location location) {
        Address addressX =
                new Address(address.getId(), address.getCountry(), address.getCity(), address.getCounty(), address
                        .getStreetAddress());
        location.setId(id);
        location.setName(name);
        location.setAddress(addressX);
    }

    public static LocationDTO of(Location entity) {
//        Address addressX=new Address(entity.getId(),entity.getAddress().getCountry(),entity.getAddress().getCity(),entity.getAddress().getCounty(),entity.getAddress().getStreetAddress());
//        LocationDTO locationX= new LocationDTO();


        return LocationDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .build();
    }
}


