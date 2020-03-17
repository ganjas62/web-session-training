package com.example.sessiontraining.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@EnableWebFluxSecurity
public class SecurityConfiguration {

  @Bean
  public ServerSecurityContextRepository serverSecurityContextRepository() {
    WebSessionServerSecurityContextRepository repository =
        new WebSessionServerSecurityContextRepository();
    return repository;
  }

  @Bean
  public ServerLogoutHandler serverLogoutHandler() {
    return new LogoutHandler(serverSecurityContextRepository());
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http
        .authorizeExchange()
        .pathMatchers("/session")
        .hasAnyRole("ADMIN")
        .anyExchange()
        .authenticated()
        .and()
        .httpBasic()
        .securityContextRepository(serverSecurityContextRepository())
        .and()
        .formLogin();
    http.csrf().disable();
    http.logout().logoutHandler(serverLogoutHandler());

    return http.build();
  }

  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
    UserDetails user = User.withDefaultPasswordEncoder()
        .username("user")
        .password("password")
        .roles("USER")
        .build();
    UserDetails admin = User.withDefaultPasswordEncoder()
        .username("admin")
        .password("password")
        .roles("ADMIN")
        .build();
    return new MapReactiveUserDetailsService(user, admin);
  }
}
