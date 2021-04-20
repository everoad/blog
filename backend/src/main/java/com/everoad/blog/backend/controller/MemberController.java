package com.everoad.blog.backend.controller;

import com.everoad.blog.backend.core.dto.ApiResponse;
import com.everoad.blog.backend.core.lib.Const;
import com.everoad.blog.backend.dto.member.MemberInfoDto;
import com.everoad.blog.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users", produces = Const.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/me")
  public ApiResponse<?> me(@RequestParam("token") String token) {
    MemberInfoDto dto = memberService.selectMemberByToken(token);
    return new ApiResponse<>(dto);
  }
}
