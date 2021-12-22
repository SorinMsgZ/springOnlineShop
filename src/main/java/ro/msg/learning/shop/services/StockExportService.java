package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ro.msg.learning.shop.dto.StockExportDTO;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@Validated
@RequiredArgsConstructor
public class StockExportService {

    private final StockRepository stockRepository;

    public List<StockExportDTO> exportingStockByLocationId(int locationId) {
        return stockRepository.findByLocation_Id(locationId).stream().map(StockExportDTO::of)
                .collect(Collectors.toList());
    }

    public List<StockExportDTO> importStocks(List<StockExportDTO> input) {
        List<Stock> stockList =
                input.stream().map(StockExportDTO::toEntity).collect(Collectors.toList());
//        Stock stock = input.toEntity();
        return stockList.stream().map(stock -> StockExportDTO.of(stockRepository.save(stock)))
                        .collect(Collectors.toList());
    }

}