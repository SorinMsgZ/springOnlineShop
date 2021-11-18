package ro.msg.learning.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.repositories.*;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }


    @Bean
    public CommandLineRunner onStart(SupplierRepository repository) {
        return args -> {
			/*System.out.println("Find by FirstName: ");
			System.out.println(repository.findByName("Carex caroliniana Schwein."));*/

            System.out.println("Find all: ");
            System.out.println(repository.findAll());

            /*System.out.println("Find by id: ");
            System.out.println(repository.findById(1, 1));*/

           /* Supplier furnizor = new Supplier();
            furnizor.setName("Hercule3");
            System.out.println(repository.save(furnizor));*/

            /*repository.deleteAll(repository.findByName("Hercule2"));

            Supplier flor = repository.findById(1).orElseThrow(RuntimeException::new);
            flor.setName("Blax");
            repository.save(flor);
            System.out.println(repository.findById(1));*/

        };
    }

}
