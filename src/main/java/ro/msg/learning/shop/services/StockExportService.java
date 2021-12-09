package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockExportService {

    private final StockRepository stockRepository;

    public List<StockDTO> exporting(int locationId) {
        return stockRepository.findAll().stream()
                .map(StockDTO::of).filter(stockDTO -> stockDTO.getLocation().getId() == locationId)
                .collect(Collectors.toList());
    }
}
