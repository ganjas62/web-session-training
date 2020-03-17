package com.example.sessiontraining.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
public class HelloController {

  @GetMapping
  public Mono<String> getHello(Principal principal) {
    return Mono.just("Hello, " + principal.getName());
  }
}
