package com.everoad.blog.backend.controller;

import com.everoad.blog.backend.core.lib.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/categories", produces = Const.APPLICATION_JSON_VALUE)
public class CategoryController {


}
