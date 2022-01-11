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
@RequiredArgsConstructor
@Profile("TestProfile2")
public class ClearPopulateDbController {

    private final ProductService productService;
    private final AddressService addressService;
    private final LocationService locationService;
    private final OrderService orderService;
    private final OrderCreatorService orderCreatorService;
    private final ProductCategoryService productCategoryService;
    private final StockService stockService;
    private final SupplierService supplierService;
    private final CustomerService customerService;

    @PostMapping("/db/customers")
    public CustomerDTO createCustomer(@RequestBody CustomerDTO body) {
        return customerService.create(body);
    }

    @DeleteMapping("/db/customers/{id}")
    public void deleteCustomerById(@PathVariable int id) {
        customerService.deleteById(id);
    }

    @DeleteMapping("/db/customers")
    public void deleteAllCustomers() {
        customerService.deleteAll();
    }

    @PostMapping("/db/products")
    public ProductDTO createProduct(@RequestBody ProductDTO body) {
        return productService.create(body);
    }

    @DeleteMapping("/db/products/{id}")
    public void deleteProductById(@PathVariable int id) {
        productService.deleteById(id);
    }

    @DeleteMapping("/db/products")
    public void deleteAllProducts() {
        productService.deleteAll();
    }

    @PostMapping("/db/addresses")
    public AddressDTO createAddress(@RequestBody AddressDTO body) {
        return addressService.create(body);
    }

    @DeleteMapping("/db/addresses/{streetAddress}")
    public void deleteAddressByStreetAddress(@PathVariable String streetAddress) {
        addressService.deleteByStreetAddress(streetAddress);
    }

    @DeleteMapping("/db/addresses")
    public void deleteAllAddresses() {
        addressService.deleteAll();
    }

    @PostMapping("/db/locations")
    public LocationDTO createLocation(@RequestBody LocationDTO body) {
        return locationService.create(body);
    }

    @DeleteMapping("/db/locations/{name}")
    public void deleteLocationByName(@PathVariable String name) {
        locationService.deleteByName(name);
    }

    @DeleteMapping("/db/locations")
    public void deleteAllLocations() {
        locationService.deleteAll();
    }

    @PostMapping("/db/orders")
    public List<OrderDTO> createOrder(@RequestBody OrderObjectInputDTO body) {

        return orderCreatorService.createOrder(body);
    }

    @DeleteMapping("/db/orders/{id}")
    public void deleteOrderById(@PathVariable int id) {
        orderService.deleteById(id);
    }

    @DeleteMapping("/db/orders")
    public void deleteAllOrders() {
        orderService.deleteAll();
    }

    @PostMapping("/db/productcategories")
    public ProductCategoryDTO createProductCategory(@RequestBody ProductCategoryDTO body) {
        return productCategoryService.create(body);
    }

    @DeleteMapping("/db/productcategories/{name}")
    public void deleteProductCategoryByName(@PathVariable String name) {
        productCategoryService.deleteByName(name);
    }

    @DeleteMapping("/db/productcategories")
    public void deleteAllProductCategories() {
        productCategoryService.deleteAll();
    }

    @PostMapping("/db/stocks")
    public StockDTO createStock(@RequestBody StockDTO body) {
        return stockService.create(body);
    }

    @DeleteMapping("/db/stocks/{id}")
    public void deleteStockById(@PathVariable StockId id) {
        stockService.deleteById(id);
    }

    @DeleteMapping("/db/stocks")
    public void deleteAllStocks() {
        stockService.deleteAll();
    }

    @PostMapping("/db/suppliers")
    public SupplierDTO createSupplier(@RequestBody SupplierDTO body) {
        return supplierService.create(body);
    }

    @DeleteMapping("/db/suppliers/{name}")
    public void deleteSupplierByName(@PathVariable String name) {
        supplierService.deleteByName(name);
    }

    @DeleteMapping("/db/suppliers")
    public void deleteAllSuppliers() {
        supplierService.deleteAll();
    }
}
