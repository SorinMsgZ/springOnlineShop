package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.entities.StockId;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, StockId> {
    List<Stock> findByLocation_Id(int id);
}
