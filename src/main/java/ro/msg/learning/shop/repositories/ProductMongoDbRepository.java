package ro.msg.learning.shop.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.msg.learning.shop.entities.ProductMongoDb;

import java.util.Optional;

public interface ProductMongoDbRepository extends MongoRepository<ProductMongoDb, Integer> {
    Optional<ProductMongoDb> findById(Integer id);
}
