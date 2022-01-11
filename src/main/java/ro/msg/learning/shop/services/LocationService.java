package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.LocationDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.AddressRepository;
import ro.msg.learning.shop.repositories.LocationRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationService {
    public final LocationRepository locationRepository;
    public final AddressRepository addressRepository;

    public List<LocationDTO> listAll() {

        return locationRepository.findAll().stream()
                .map(LocationDTO::of)
                .collect(Collectors.toList());
    }

    public LocationDTO readByName(String name) {
        return locationRepository.findByName(name)
                .map(LocationDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public LocationDTO create(LocationDTO input) {
        Location location = input.toEntity();
        location.setAddress(addressRepository.findById((input.getAddress().getId())).orElseThrow(NotFoundException::new));
        return LocationDTO.of(locationRepository.save(location));
    }

    public void deleteByName(String name) {
        locationRepository.deleteByName(name);
    }

    public LocationDTO updateByName(String name, LocationDTO input) {
        Location location = locationRepository.findByName(name).orElseThrow(NotFoundException::new);
        input.copyToEntity(location);
        locationRepository.save(location);
        return LocationDTO.of(location);
    }
}
