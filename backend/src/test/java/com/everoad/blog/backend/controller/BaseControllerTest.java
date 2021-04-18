package com.everoad.blog.backend.controller;

import com.everoad.blog.backend.auth.AuthTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class BaseControllerTest {

  @Autowired
  public MockMvc mockMvc;

  @Autowired
  public ObjectMapper objectMapper;

  public String accessToken;

  @BeforeEach
  public void setUp() throws Exception {
    AuthTest authTest = new AuthTest();
    if (StringUtils.isEmpty(this.accessToken)) {
      this.accessToken = authTest.getAccessToken(mockMvc);
    }
  }

  public <T> ResultActions postRequest(String url, T content) throws Exception {
    MockHttpServletRequestBuilder builder = post(url)
        .header(HttpHeaders.AUTHORIZATION, accessToken)
        .contentType(MediaType.APPLICATION_JSON);

    if (content != null) {
      builder.content(objectMapper.writeValueAsString(content));
    }

    return mockMvc.perform(builder);
  }

  public <T> ResultActions deleteRequest(String url, T content) throws Exception {
    MockHttpServletRequestBuilder builder = delete(url)
        .header(HttpHeaders.AUTHORIZATION, accessToken)
        .contentType(MediaType.APPLICATION_JSON);

    if (content != null) {
      builder.content(objectMapper.writeValueAsString(content));
    }

    return mockMvc.perform(builder);
  }

  public <T> ResultActions putRequest(String url, T content) throws Exception {
    MockHttpServletRequestBuilder builder = put(url)
        .header(HttpHeaders.AUTHORIZATION, accessToken)
        .contentType(MediaType.APPLICATION_JSON);

    if (content != null) {
      builder.content(objectMapper.writeValueAsString(content));
    }

    return mockMvc.perform(builder);
  }

  public ResultActions getRequest(String url, Map<String, String> params) throws Exception {
    MockHttpServletRequestBuilder builder = get(url)
        .header(HttpHeaders.AUTHORIZATION, accessToken);

    if (params != null) {
      MultiValueMap<String, String> ps = new LinkedMultiValueMap<>();
      ps.setAll(params);
      builder.params(ps);
    }
    return mockMvc.perform(builder);
  }

  public <T> ResultActions postRequestAnonymous(String url, T content) throws Exception {
    MockHttpServletRequestBuilder builder = post(url)
        .contentType(MediaType.APPLICATION_JSON);

    if (content != null) {
      builder.content(objectMapper.writeValueAsString(content));
    }

    return mockMvc.perform(builder);
  }

  public ResultActions getRequestAnonymous(String url, Map<String, String> params) throws Exception {
    MockHttpServletRequestBuilder builder = get(url);
    if (params != null) {
      MultiValueMap<String, String> ps = new LinkedMultiValueMap<>();
      ps.setAll(params);
      builder.params(ps);
    }
    return mockMvc.perform(builder);
  }
}
