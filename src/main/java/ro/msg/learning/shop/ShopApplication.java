package ro.msg.learning.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
        System.out.println("The \"main()\" method is running after \"CommandLiner\"");
    }

    /*@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx){
            return args->{
                System.out.println("\"CommandLiner\" is running before \"main()\" method!");
                System.out.println("Inspect the beans provided by Spring Boot");
                String[] beanNames =ctx.getBeanDefinitionNames();
                Arrays.sort(beanNames);
                for (String beanName:beanNames){
                    System.out.println(beanName);
                }
            };
    }*/
    /*@Bean
    public CommandLineRunner commandLineRunner(OrderCreationService orderCreationService) {
        return args -> {
            Address deliveryAddress = new Address();
            deliveryAddress.setId(1);
            deliveryAddress.setCountry("United States");
            deliveryAddress.setCity("Rochester");
            deliveryAddress.setCounty("New York");
            deliveryAddress.setStreetAddress("440 Merry Drive");

            ProdOrdCreationDTO prod1 = new ProdOrdCreationDTO(1,95);
           *//* prod1.setProductID(1);
            prod1.setProductQty(10);*//*

            ProdOrdCreationDTO prod2 = new ProdOrdCreationDTO(2,47);
           *//* prod1.setProductID(2);
            prod1.setProductQty(1);*//*

            List<ProdOrdCreationDTO> listProduct=new ArrayList<>();
            listProduct.add(prod1);
            listProduct.add(prod2);
            OrderCreationDTO orderCreationDTO = new OrderCreationDTO();

            orderCreationDTO.setCreatedAt(LocalDateTime.of(LocalDate.of(2021, 02, 21), LocalTime.of(12, 30, 0)));
            orderCreationDTO.setDeliveryAddress(deliveryAddress);
            orderCreationDTO.setProduct(listProduct);
            orderCreationService.createOrder(orderCreationDTO);

        };
    }*/

    ;
//}
   /* @Bean
    public CommandLineRunner commandLineRunner(ProductService productService){
        return args->{
            int id = 1;
            String name = "TestNameDTO";
            String description = "Test Product Description";
            BigDecimal price = new BigDecimal("10.13");
            double weight = 100;
            int productCategoryId = 1;
            String productCategoryName = "TestNameProdCat";
            String productCategoryDescription = "Test Product Category Description";
            int supplierId = 1;
            String supplierName = "TestNameSupplier";
            String imageUrl = "www.TestUrl.com";

            ProductDTO productDTO =
                    new ProductDTO(id, name, description, price, weight, productCategoryId, productCategoryName, productCategoryDescription, supplierId, supplierName, imageUrl);
            productService.createProduct(productDTO);
            };
        }*/

}
