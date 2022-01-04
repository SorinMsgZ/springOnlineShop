package ro.msg.learning.shop.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ro.msg.learning.shop.services.DataBaseUserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("with-basic")
public class WithBasicSecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataBaseUserService userDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api").anonymous()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .realmName("Application Name: OnlineShop with-basic Authentication")
                .and().csrf().disable();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
