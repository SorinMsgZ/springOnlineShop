package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.LocationDTO;
import ro.msg.learning.shop.dto.SupplierDTO;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.LocationRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationService {
    public final LocationRepository locationRepository;
    public List<LocationDTO> listSupplier() {

        return StreamSupport.stream(locationRepository.findAll().spliterator(), false)
                .map(LocationDTO::of)
                .collect(Collectors.toList());
    }

   public LocationDTO readSingleLocation(int id) {
        return locationRepository.findById(id)
                .map(LocationDTO::of)
                .orElseThrow(NotFoundException::new);
    }
}
