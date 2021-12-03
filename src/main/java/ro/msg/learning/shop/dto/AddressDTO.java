package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.Address;

@Data
@Builder
public class AddressDTO {
    private  int id;
    private  String country;
    private   String city;
    private  String county;
    private String streetAddress;

    public AddressDTO(int id, String country, String city, String county, String streetAddress) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.county = county;
        this.streetAddress = streetAddress;
    }

    public Address toEntity() {
        Address result = new Address();
        result.setId(id);
        result.setCountry(country);
        result.setCity(city);
        result.setCounty(county);
        result.setStreetAddress(streetAddress);
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Address address) {
        address.setId(id);
        address.setCountry(country);
        address.setCity(city);
        address.setCounty(county);
        address.setStreetAddress(streetAddress);
    }

    public static AddressDTO of(Address entity) {
        return AddressDTO.builder()
                .id(entity.getId())
                .country(entity.getCountry())
                .city(entity.getCity())
                .county(entity.getCounty())
                .streetAddress(entity.getStreetAddress())
                .build();
    }
}

