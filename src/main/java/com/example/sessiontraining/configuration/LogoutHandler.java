package com.example.sessiontraining.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

public class LogoutHandler implements ServerLogoutHandler {

  private ServerSecurityContextRepository repository;

  public LogoutHandler(ServerSecurityContextRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<Void> logout(WebFilterExchange webFilterExchange, Authentication authentication) {
    return webFilterExchange.getExchange()
        .getSession()
        .doOnNext(WebSession::invalidate)
        .then(repository.save(webFilterExchange.getExchange(), null));
  }
}
