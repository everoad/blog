package com.everoad.blog.backend.auth;

import com.everoad.blog.backend.core.auth.AuthToken;
import com.everoad.blog.backend.core.dto.ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles({"test"})
@SpringBootTest
public class AuthTest {

  @Autowired
  MockMvc mockMvc;


  @Test
  public void 인증() throws Exception {
    getAccessToken(mockMvc);
  }

  public String getAccessToken(MockMvc mockMvc) throws Exception {
    // Given
    Map<String, Object> content = Map.of("username", "tester", "password", 1234);
    ObjectMapper objectMapper = new ObjectMapper();
    // When & Then
    String result = mockMvc.perform(post("/auth/login")
        .content(objectMapper.writeValueAsString(content)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("body.accessToken").exists())
        .andExpect(jsonPath("body.refreshToken").exists())
        .andExpect(jsonPath("body.accessTokenExpiredAt").exists())
        .andExpect(jsonPath("body.refreshTokenExpiredAt").exists())
        .andReturn().getResponse().getContentAsString();
    ApiResponse<Map<String, Object>> authToken = objectMapper.readValue(result, new TypeReference<>() {});
    return "Bearer " + authToken.getBody().get("accessToken");
  }


}
