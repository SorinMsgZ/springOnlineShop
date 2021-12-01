package ro.msg.learning.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.entities.StockId;

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

    public void updateStock(){
        int productId = subject.getOrderObject().prod.getId();
        int locId = subject.getOrderObject().loc.getId();
        StockId stockId = new StockId(productId,locId);
        Product product=subject.getOrderObject().prod;
        Location location =subject.getOrderObject().loc;
        int qty = subject.getOrderObject().qty;

        int oldQty=stockService.readSingleStock(stockId).getQuantity();
        int newQty=oldQty-qty;
        StockDTO stockDTO =new StockDTO(stockId,product,location,newQty);

        stockService.updateStock(stockId,stockDTO);
    }
}
