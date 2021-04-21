package com.everoad.blog.backend.controller;

import com.everoad.blog.backend.core.dto.ApiResponse;
import com.everoad.blog.backend.dto.member.MemberSaveDto;
import com.everoad.blog.backend.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class IndexController {

  private final MemberService memberService;

  @PostMapping("/register")
  public ApiResponse<?> register(@Valid @RequestBody MemberSaveDto saveDto) {
    memberService.register(saveDto);
    return new ApiResponse<>();
  }

}
