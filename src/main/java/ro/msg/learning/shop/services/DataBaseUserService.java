package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entities.User;
import ro.msg.learning.shop.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseUserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .map(DataBaseUserService::mapUser)
                .orElseThrow(() -> new UsernameNotFoundException("The requested user was not found!"));
    }

    private static UserDetails mapUser(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), List
                .of(new SimpleGrantedAuthority(user.getCustomer() == null ? "ROLE_CUSTOMER" : "ROLE_NOCUSTOMER")));
    }

}
