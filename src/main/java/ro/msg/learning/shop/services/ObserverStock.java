package ro.msg.learning.shop.services;

import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.StockId;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Transactional
public class ObserverStock extends ObserverObject {

    StockService stockService;

    public ObserverStock(OrderBasket subject, StockService stockService) {
        super(subject);
        this.subject.attach(this);
        this.stockService = stockService;
    }

    @Override
    public void update() {
        updateStock();
    }

    public void updateStock() {
        int productId = subject.getOrderObject().prod.getId();
        int locId = subject.getOrderObject().loc.getId();
//TODO Check if the commented code below  works as an alternative to the stream
//        StockId stockIdX = new StockId(productId, locId);
//        StockId stockId=stockService.readSingleStock(stockIdX).getIdProductLocation();

        StockId stockId = stockService.listStock()
                .stream()
                .filter(stockDTO -> (stockDTO.getProductId() == productId) && (stockDTO.getLocationId() == locId))
                .collect(Collectors
                        .toList()).get(0).toEntity().getIdProductLocation();


        Product product = subject.getOrderObject().prod;
        Location location = subject.getOrderObject().loc;
        int qty = subject.getOrderObject().qty;

        int oldQty = stockService.readSingleStock(stockId).getQuantity();
        int newQty = oldQty - qty;
        StockDTO stockDTO = new StockDTO(productId, locId, product, location, newQty);

        stockService.updateStock(stockId, stockDTO);
    }
}
