package com.example.ss2login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfiguration {

    @Configuration
    @Order(1)
    public static class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            // @formatter:off
            http
                .antMatcher("/admin/**")
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
            .and()
                .formLogin()
                .loginPage("/admin-login")
                .loginProcessingUrl("/admin-login")
                .failureUrl("/admin-login?error")
                .defaultSuccessUrl("/admin/home")
            .and()
                .logout()
                .logoutUrl("/admin-logout")
                .logoutSuccessUrl("/admin-login?logout")
                .permitAll()
            .and()
                .csrf().disable();
            // @formatter:on
        }
    }

    @Configuration
    @Order(2)
    public static class UserSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            // @formatter:off
            http
                .antMatcher("/user/home/**")
                .authorizeRequests()
                .antMatchers("/user/home/**").hasAuthority("ROLE_USER")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/user-login")
                .loginProcessingUrl("/user-login")
                .failureUrl("/user-login?error")
                .defaultSuccessUrl("/user/home")
            .and()
                .logout()
                .logoutUrl("/user-logout")
                .logoutSuccessUrl("/user-login?logout")
            .and()
                .csrf().disable();
            // @formatter:on
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        User.UserBuilder users = User.builder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password(passwordEncoder().encode("user")).roles("USER").build());
        manager.createUser(users.username("admin").password(passwordEncoder().encode("admin")).roles("USER", "ADMIN").build());
        return manager;
    }

}