package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.StockExportDTO;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockExportService {

    private final StockRepository stockRepository;

    public List<StockExportDTO> exportingStockByLocationId(int locationId) {
        return stockRepository.findAll().stream()
                .map(StockExportDTO::new).filter(stockDTO -> stockDTO.getLocationId() == locationId)
                .collect(Collectors.toList());
    }
}
