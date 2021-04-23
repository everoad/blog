package com.everoad.blog.backend.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  boolean existsByCategoryId(Integer categoryId);

}
