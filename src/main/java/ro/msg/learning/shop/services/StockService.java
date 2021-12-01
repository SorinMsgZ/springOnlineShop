package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.entities.StockId;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.StockRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
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

    public StockDTO updateStock(StockId id, StockDTO input) {
        Stock stock = stockRepository.findById(id).orElseThrow(NotFoundException::new);
        input.copyToEntity(stock);
        stockRepository.save(stock);
        return StockDTO.of(stock);

    }
}
