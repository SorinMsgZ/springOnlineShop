package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.RevenueDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Revenue;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.RevenueRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RevenueService {
    private final RevenueRepository revenueRepository;

    public List<RevenueDTO> listAll() {
        return revenueRepository.findAll().stream()
                .map(RevenueDTO::of)
                .collect(Collectors.toList());
    }

    public RevenueDTO readByLocation(Location location) {
        return revenueRepository.findByLocation(location)
                .map(RevenueDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public RevenueDTO create(RevenueDTO input) {
        Revenue revenue = input.toEntity();
        return RevenueDTO.of(revenueRepository.save(revenue));
    }

    public void deleteByLocation(Location location) {
        revenueRepository.deleteByLocation(location);
    }

    public RevenueDTO updateByLocation(Location location, RevenueDTO input) {
        Revenue revenue = revenueRepository.findByLocation(location).orElseThrow(NotFoundException::new);
        input.copyToEntity(revenue);
        revenueRepository.save(revenue);
        return RevenueDTO.of(revenue);
    }
}
