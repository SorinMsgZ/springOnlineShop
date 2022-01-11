package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.AddressDTO;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.AddressRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public List<AddressDTO> listAll() {
        return addressRepository.findAll().stream()
                .map(AddressDTO::of)
                .collect(Collectors.toList());
    }
    public AddressDTO readByStreetAddress(String streetAddress) {
        return addressRepository.findByStreetAddress(streetAddress)
                .map(AddressDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public AddressDTO create(AddressDTO input) {
        Address address = input.toEntity();
        return AddressDTO.of(addressRepository.save(address));
    }

    public void deleteByStreetAddress(String streetAddress) {
        addressRepository.deleteByStreetAddress(streetAddress);
    }
    public void deleteAll() {
        addressRepository.deleteAll();
    }

    public AddressDTO updateByStreetAddress(String streetAddress, AddressDTO input) {
        Address address = addressRepository.findByStreetAddress(streetAddress).orElseThrow(NotFoundException::new);
        input.copyToEntity(address);
        addressRepository.save(address);
        return AddressDTO.of(address);
    }
}
