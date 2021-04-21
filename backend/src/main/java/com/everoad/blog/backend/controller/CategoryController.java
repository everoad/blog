package com.everoad.blog.backend.controller;

import com.everoad.blog.backend.core.dto.ApiResponse;
import com.everoad.blog.backend.core.lib.Const;
import com.everoad.blog.backend.dto.category.CategoryInfoDto;
import com.everoad.blog.backend.dto.category.CategorySaveDto;
import com.everoad.blog.backend.service.CategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/categories", produces = Const.APPLICATION_JSON_VALUE)
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public ApiResponse<?> getCategoryList() {
    List<CategoryInfoDto> list = categoryService.selectCategoryListWithPostCount();
    return new ApiResponse<>(list);
  }

  @GetMapping("/{categoryId}")
  public ApiResponse<?> getCategory(@PathVariable Integer categoryId) {
    CategoryInfoDto dto = categoryService.selectCategory(categoryId);
    return new ApiResponse<>(dto);
  }

  @PostMapping
  public ApiResponse<?> addCategory(@Valid @RequestBody List<CategorySaveDto> saveDtoList) {
    categoryService.updateCategoryList(saveDtoList);
    return new ApiResponse<>();
  }

}
