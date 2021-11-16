package ro.msg.learning.shop.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.entities.StockId;

public interface StockRepository extends CrudRepository<Stock, StockId> {
}
