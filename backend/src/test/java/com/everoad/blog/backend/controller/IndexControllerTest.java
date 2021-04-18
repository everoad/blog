package com.everoad.blog.backend.controller;

import com.everoad.blog.backend.dto.member.MemberSaveDto;
import com.everoad.blog.backend.util.GenerateUtils;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class IndexControllerTest extends BaseControllerTest {

  @Test
  public void 회원가입() throws Exception {
    //given
    MemberSaveDto saveDto = GenerateUtils.generateMemberDto(0);

    //when
    this.postRequestAnonymous("/register", saveDto)
        .andDo(print())
        .andExpect(jsonPath("success").value(true))
        .andExpect(jsonPath("code").value(200));

    //then

  }
}