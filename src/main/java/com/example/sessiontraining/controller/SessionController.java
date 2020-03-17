package com.example.sessiontraining.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/session")
@RestController
public class SessionController {

  @PutMapping
  public Mono<Map<String, String>> putSession(String key, String value, WebSession webSession) {
    webSession.getAttributes().put(key, value);
    Map<String, String> result = new HashMap<>();
    result.put(key, value);
    return Mono.just(result);
  }

  @GetMapping
  public Mono<Map<String, Object>> getSession(WebSession webSession) {
    return Mono.just(webSession.getAttributes());
  }
}
