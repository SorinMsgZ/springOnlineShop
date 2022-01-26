package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.entities.Address;

@Data
@Builder
@NoArgsConstructor
public class AddressDTO {

    private String country;
    private String city;
    private String county;
    private String streetAddress;
    private String state;

    public AddressDTO(String country, String city, String county, String streetAddress, String state) {

        this.country = country;
        this.city = city;
        this.county = county;
        this.streetAddress = streetAddress;
        this.state = state;
    }

    public Address toEntity() {
        Address result = new Address();
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Address address) {

        address.setCountry(country);
        address.setCity(city);
        address.setCounty(county);
        address.setStreetAddress(streetAddress);
        address.setState(state);
    }

    public static AddressDTO of(Address entity) {
        return AddressDTO.builder()

                .country(entity.getCountry())
                .city(entity.getCity())
                .county(entity.getCounty())
                .streetAddress(entity.getStreetAddress())
                .state(entity.getState())
                .build();
    }
}

