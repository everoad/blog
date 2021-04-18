package com.everoad.blog.backend.controller;

import com.everoad.blog.backend.core.dto.ApiResponse;
import com.everoad.blog.backend.dto.post.PostSaveDto;
import com.everoad.blog.backend.util.GenerateUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest extends BaseControllerTest {

  private final String baseUrl = "/posts/";

  @Test
  public void 게시글_추가() throws Exception {
    //given
    PostSaveDto postSaveDto = GenerateUtils.generatePostDto(0);

    //when
    ResultActions actions = this.postRequest(baseUrl, postSaveDto);

    //then
    actions
        .andExpect(status().isOk())
        .andExpect(jsonPath("success").value(true))
        .andExpect(jsonPath("code").value(200))
        .andExpect(jsonPath("body").isNotEmpty());
  }

  @Test
  public void 게시글_수정() throws Exception {
    //given
    PostSaveDto saveDto = GenerateUtils.generatePostDto(0);
    ResultActions saveActions = this.postRequest(baseUrl, saveDto);
    String content = saveActions.andReturn().getResponse().getContentAsString();
    ApiResponse<Integer> apiResponse = objectMapper.readValue(content, new TypeReference<>() {
    });
    Integer postId = apiResponse.getBody();

    //when
    PostSaveDto editDto = GenerateUtils.generatePostDto(1);
    ResultActions editActions = this.putRequest(baseUrl + postId, editDto);

    //then
    editActions.andDo(print())
        .andExpect(jsonPath("success").value(true))
        .andExpect(jsonPath("code").value(200));

    ResultActions findActions = this.getRequest(baseUrl + postId, null);
    findActions
        .andDo(print())
        .andExpect(jsonPath("success").value(true))
        .andExpect(jsonPath("code").value(200))
        .andExpect(jsonPath("body.title").value(editDto.getTitle()))
        .andExpect(jsonPath("body.description").value(editDto.getDescription()));
  }

  @Test
  public void 게시글_삭제() throws Exception {
    //given
    PostSaveDto saveDto = GenerateUtils.generatePostDto(0);
    ResultActions saveActions = this.postRequest(baseUrl, saveDto);
    String content = saveActions.andReturn().getResponse().getContentAsString();
    ApiResponse<Integer> apiResponse = objectMapper.readValue(content, new TypeReference<>() {
    });
    Integer postId = apiResponse.getBody();

    //when
    this.deleteRequest(baseUrl + postId, null)
        .andDo(print())
        .andExpect(jsonPath("success").value(true))
        .andExpect(jsonPath("code").value(200));

    //then
    this.getRequest(baseUrl + postId, null)
        .andDo(print())
        .andExpect(jsonPath("success").value(false))
        .andExpect(jsonPath("code").value(404));
  }

  @Test
  public void 게시글_목록_조회() throws Exception {
    //given
    List<PostSaveDto> saveList = IntStream.range(0, 20).mapToObj(GenerateUtils::generatePostDto).collect(Collectors.toList());
    for (PostSaveDto dto : saveList) {
      this.postRequest(baseUrl, dto);
    }
    //when
    Map<String, String> params = new HashMap<>();
    String size = "15";
    String page = "0";
    params.put("size", size);
    params.put("page", page);

    //then
    this.getRequest(baseUrl, params)
        .andDo(print())
        .andExpect(jsonPath("body.content.length()").value(15))
        .andExpect(jsonPath("body.totalElements").value(20))
        .andExpect(jsonPath("body.size").value(15));
  }

}