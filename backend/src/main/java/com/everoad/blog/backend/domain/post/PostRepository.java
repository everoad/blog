package com.everoad.blog.backend.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {


  @Query("select p from Post p join fetch p.category where p.id = :id")
  Optional<Post> findWithCategoryById(@Param("id") Long postId);

  boolean existsByCategoryId(Integer categoryId);

}
