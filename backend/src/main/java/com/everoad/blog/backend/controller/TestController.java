package com.everoad.blog.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/api/test")
public class TestController {

  @RequestMapping(value = "/ping")
  public String pingPong() {
    return "pong";
  }

}
