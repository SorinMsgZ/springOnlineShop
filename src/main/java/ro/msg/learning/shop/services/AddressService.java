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
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public List<AddressDTO> listAddress() {
        return StreamSupport.stream(addressRepository.findAll().spliterator(), false)
                .map(AddressDTO::of)
                .collect(Collectors.toList());
    }

    public AddressDTO readSingleAddress(int id) {
        return addressRepository.findById(id)
                .map(AddressDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public AddressDTO createAddress(AddressDTO input) {
        Address address = input.toEntity();


        return AddressDTO.of(addressRepository.save(address));
    }

    public void deleteAddress(int id) {
        addressRepository.deleteById(id);
    }

    public AddressDTO updateAddress(int id, AddressDTO input) {
        Address address = addressRepository.findById(id).orElseThrow(NotFoundException::new);
        input.copyToEntity(address);
        addressRepository.save(address);
        return AddressDTO.of(address);
    }
}
