package com.everoad.blog.backend.core.config;

import com.everoad.blog.backend.core.security.MemberAdapter;
import com.everoad.blog.backend.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.util.Optional;

@Configuration
@EnableCaching
@EnableJpaAuditing
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class ApplicationConfig implements WebMvcConfigurer {

  private final AppProperties appProperties;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String uploadPath = appProperties.getUploadPath();
    registry.addResourceHandler("/images/**")
        .addResourceLocations("file:///" + uploadPath + "/")
        .setCachePeriod(3600)
        .resourceChain(true)
        .addResolver(new PathResourceResolver());
  }

  @Bean
  public AuditorAware<String> auditorProvider() {
    return () -> {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication.isAuthenticated()) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof MemberAdapter) {
          MemberAdapter adapter = (MemberAdapter) principal;
          return Optional.ofNullable(adapter.getMember().getId());
        } else {
          return Optional.of(String.valueOf(principal));
        }
      }
      return Optional.empty();
    };
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
