package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
import ro.msg.learning.shop.exceptions.StockNotFound;
import ro.msg.learning.shop.repositories.OrderDetailRepository;
import ro.msg.learning.shop.repositories.OrderRepository;


import javax.transaction.Transactional;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class OrderCreationService  {
    private final OrderRepository orderRepository;
    private final Context context;


    public void createOrder(OrderCreationDTO input) {

        List<ProdOrdCreationDTO> listProducts = input.getProduct();

        class ObjectStructure {

            Location loc;
            int prodId;
            int qty;

            public ObjectStructure(Location loc, int prodId, int qty) {
                this.loc = loc;
                this.prodId = prodId;
                this.qty = qty;
            }

            @Override
            public String toString() {
                return "ObjectStructure{" +
                        "loc=" + loc +
                        ", prodId=" + prodId +
                        ", qty=" + qty +
                        '}';
            }
        }

        List<ObjectStructure> objectStructureList = new ArrayList<>();

//        Context context = new Context();
        for (ProdOrdCreationDTO product : listProducts) {
            try {
                Location location = context.executeStrategy(product.getProductID(), product.getProductQty());
                ObjectStructure object = new ObjectStructure(location, product.getProductID(), product.getProductQty());
                objectStructureList.add(object);
            } catch (Exception exception) {

                System.out.println(exception.getMessage());
                System.out.println(product.toString());
            }

        }
        System.out.println("Nr. obiectelor gasite in stock: " + objectStructureList.size());
        System.out.println("Obiectele sunt:" + objectStructureList.toString());

        //strategy=> method: findLocationAndTakeProducts()
        //select strategy based on @Configuration
        //Strategy1 = Single location
        //Strategy2 = Most abundant
        //Service runs the strategy

        //if false => Exception else ...

       /* Order order = input.toEntity();
        return OrderCreationDTO.of(orderRepository.save(order));*/
    }
}

