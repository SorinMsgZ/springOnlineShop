package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.StockId;
import ro.msg.learning.shop.services.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Profile("test")
@RequiredArgsConstructor
public class ClearPopulateDbController {

    private final ProductService productService;
    private final AddressService addressService;
    private final LocationService locationService;
    private final OrderService orderService;
    private final OrderCreatorService orderCreatorService;
    private final ProductCategoryService productCategoryService;
    private final StockService stockService;
    private final SupplierService supplierService;

    @PostMapping("/db/products")
    public ProductDTO createProduct(@RequestBody ProductDTO body) {
        return productService.create(body);
    }

    @DeleteMapping("/db/products/{id}")
    public void deleteProductById(@PathVariable int id) {
        productService.deleteById(id);
    }

    @PostMapping("/db/addresses")
    public AddressDTO createAddress(@RequestBody AddressDTO body) {
        return addressService.create(body);
    }

    @DeleteMapping("/db/addresses/{streetAddress}")
    public void deleteAddressByStreetAddress(@PathVariable String streetAddress) {
        addressService.deleteByStreetAddress(streetAddress);
    }

    @PostMapping("/db/locations")
    public LocationDTO createLocation(@RequestBody LocationDTO body) {
        return locationService.create(body);
    }

    @DeleteMapping("/db/locations/{name}")
    public void deleteLocationByName(@PathVariable String name) {
        locationService.deleteByName(name);
    }

    @PostMapping("/db/orders")
    public List<OrderDTO> createOrder(@RequestBody OrderObjectInputDTO body) {
        return orderCreatorService.createOrder(body);
    }

    @DeleteMapping("/db/orders/{id}")
    public void deleteOrderById(@PathVariable int id) {
        orderService.deleteById(id);
    }

    @PostMapping("/db/productcategories")
    public ProductCategoryDTO createProductCategory(@RequestBody ProductCategoryDTO body) {
        return productCategoryService.create(body);
    }

    @DeleteMapping("/db/productcategories/{name}")
    public void deleteProductCategoryByName(@PathVariable String name) {
        productCategoryService.deleteByName(name);
    }

    @PostMapping("/db/stocks")
    public StockDTO createStock(@RequestBody StockDTO body) {
        return stockService.create(body);
    }

    @DeleteMapping("/db/stocks/{id}")
    public void deleteStockById(@PathVariable StockId id) {
        stockService.deleteById(id);
    }

    @PostMapping("/db/suppliers")
    public SupplierDTO createSupplier(@RequestBody SupplierDTO body) {
        return supplierService.create(body);
    }

    @DeleteMapping("/db/suppliers/{name}")
    public void deleteSupplierByName(@PathVariable String name) {
        supplierService.deleteByName(name);
    }
}


