package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.entities.StockId;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class StockService {
    public final StockRepository stockRepository;

    public List<StockDTO> listStock() {
        return StreamSupport.stream(stockRepository.findAll().spliterator(), false)
                .map(StockDTO::of)
                .collect(Collectors.toList());
    }

    public StockDTO readSingleStock(StockId id) {
        return stockRepository.findById(id)
                .map(StockDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public StockDTO createStock(StockDTO input) {
        Stock stock = input.toEntity();
        return StockDTO.of(stockRepository.save(stock));
    }

    public void deleteStock(StockId id) {
        stockRepository.deleteById(id);
    }

    public StockDTO updateStock(StockId id, StockDTO input) {
        Stock stock = stockRepository.findById(id).orElseThrow(NotFoundException::new);
        input.copyToEntity(stock);
        stockRepository.save(stock);
        return StockDTO.of(stock);

    }
}
