package com.everoad.blog.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;


class BackendApplicationTests {

  @Test
  void contextLoads() {
    Map<Integer, Object> map = new HashMap<>();

    map.put(null, "hello");

    Object o = map.get(null);
    System.out.println("o = " + o);
  }

}
