package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.RevenueExportDTO;
import ro.msg.learning.shop.repositories.RevenueRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RevenueExportService {
    private final RevenueRepository revenueRepository;

    public List<RevenueExportDTO> readByDate(LocalDate localDate) {
        return revenueRepository.findByLocalDate(localDate)
                .stream()
                .map(RevenueExportDTO::of)
                .collect(Collectors.toList());
    }
}
