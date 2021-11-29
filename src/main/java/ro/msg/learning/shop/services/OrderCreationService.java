package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ro.msg.learning.shop.dto.OrderCreationDTO;
import ro.msg.learning.shop.dto.ProdOrdCreationDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.dto.SupplierDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.entities.OrderDetail;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.OrderDetailRepository;
import ro.msg.learning.shop.repositories.OrderRepository;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class OrderCreationService {
    private final OrderRepository orderRepository;

    private FindLocationStrategy strategy;

    public OrderCreationDTO createOrder(OrderCreationDTO input) {

        List<ProdOrdCreationDTO> listProducts = input.getProduct();
//        int i=listProducts.forEach(product->context.executeStrategy(product.getProductID(), product.getProductQty()));

        class ObjectStructure {

            Location loc;
            int prodId;
            int qty;

            public ObjectStructure(Location loc, int prodId, int qty) {
                this.loc = loc;
                this.prodId = prodId;
                this.qty = qty;
            }
        }

        List<ObjectStructure> objectStructureList = new ArrayList<>();

        Context context = new Context(strategy);
        for (ProdOrdCreationDTO product : listProducts) {
            try {
                Location location = context.executeStrategy(product.getProductID(), product.getProductQty());
                ObjectStructure object = new ObjectStructure(location, product.getProductID(), product.getProductQty());
                objectStructureList.add(object);
            } catch (Exception exception) {

            }
        }

        //strategy=> method: findLocationAndTakeProducts()
        //select strategy based on @Configuration
        //Strategy1 = Single location
        //Strategy2 = Most abundant
        //Service runs the strategy

        //if false => Exception else ...

        Order order = input.toEntity();
        return OrderCreationDTO.of(orderRepository.save(order));
    }
}

