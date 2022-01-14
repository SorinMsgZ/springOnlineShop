package ro.msg.learning.shop.services;


import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.StockId;


import java.util.stream.Collectors;


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

        StockId stockId = stockService.listAll()
                .stream()
                .filter(stockDTO -> (stockDTO.getProduct().getId() == productId) &&
                        (stockDTO.getLocation().getId() == locId))
                .collect(Collectors
                        .toList()).get(0).toEntity().getIdProductLocation();

        Product product = subject.getOrderObject().prod;
        Location location = subject.getOrderObject().loc;
        int qty = subject.getOrderObject().qty;

        int oldQty = stockService.readById(stockId).getQuantity();
        int newQty = oldQty - qty;

        StockDTO stockDTO = new StockDTO(product, location, newQty);
        stockService.updateById(stockId, stockDTO);
    }
}
