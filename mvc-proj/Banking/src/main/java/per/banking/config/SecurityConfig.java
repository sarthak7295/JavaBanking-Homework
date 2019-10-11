package per.banking.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import per.banking.entity.Customer;
import per.banking.repos.BankingRepo;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
         // Create 2 users for demo

    @Autowired
    BankingRepo repo;

        // Secure the endpoins with HTTP Basic authentication
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
//                    .antMatchers("/", "/home").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                        .loginPage("/login")
                    .permitAll()
                    .and()
                    .logout()
                    .permitAll();
        }

        @Bean
        @Override
        public UserDetailsService userDetailsService(){
            List<Customer> myCust = repo.findAll();
            List<UserDetails> userDetails = new ArrayList<>();
            for(Customer c : myCust) {

                UserDetails userDetail =
                        User.withDefaultPasswordEncoder().username(c.getUserName()).password("password")
                                .roles("USER")
                                .build();
                userDetails.add(userDetail);
            }
            return new InMemoryUserDetailsManager(userDetails);
        }
}