package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.Customer;
@Data
@Builder
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String emailAddress;

    public CustomerDTO(String firstName, String lastName, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public Customer toEntity() {
        Customer result = new Customer();
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Customer customer) {

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmailAddress(emailAddress);
    }

    public static CustomerDTO of(Customer entity) {
        return CustomerDTO.builder()

                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .emailAddress(entity.getEmailAddress())
                .build();
    }
}

