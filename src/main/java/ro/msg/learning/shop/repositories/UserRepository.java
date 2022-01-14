package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.entities.User;

public interface UserRepository extends JpaRepository<User,String> {
}
