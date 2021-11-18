package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.entities.StockId;

public interface StockRepository extends JpaRepository<Stock, StockId> {
}
