package com.everoad.blog.backend.core.config;

import com.everoad.blog.backend.core.auth.AuthTokenProvider;
import com.everoad.blog.backend.core.lib.Const;
import com.everoad.blog.backend.core.security.*;
import com.everoad.blog.backend.domain.member.MemberRole;
import com.everoad.blog.backend.service.MemberConnectionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomAuthenticationProvider authenticationProvider;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().mvcMatchers("/test/**");
  }

  /**
   * ?????? ?????? ????????? Token ??? ???????????? Actuator ??? Basic Authentication ??? ?????????.
   * ?????? ????????? ?????? HttpSecurity ??? ?????? ????????? ??????.
   * actuator ????????? ??????????????? ??????.
   */
  @Order(1)
  @Configuration
  public static class ActuatorWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Value(value = "${spring.security.user.name:#{null}}")
    private String username;

    @Value(value = "${spring.security.user.password:#{null}}")
    private String password;

    @Value(value = "${spring.security.user.roles:#{null}}")
    private String[] roles;

    @Bean
    public ActuatorAuthenticationFilter actuatorAuthenticationFilter() {
      return new ActuatorAuthenticationFilter(username, password, roles);
    }

    /**
     * actuator endpoint ??? ????????? ????????? ???????????? ?????? ????????? ?????? HttpSecurity ??? ?????????.
     * Basic ??????.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
          .csrf().disable()
          .antMatcher("/actuator/**")
          .authorizeRequests()
          .anyRequest().hasAnyRole(getRoles())
          .and()
          .addFilterBefore(actuatorAuthenticationFilter(), BasicAuthenticationFilter.class)
          .httpBasic()
          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private String[] getRoles() {
      if (roles == null || roles.length == 0) {
        return new String[]{Const.ACTUATOR};
      }
      return roles;
    }
  }

  @Order(2)
  @Configuration
  @RequiredArgsConstructor
  public static class ApiWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final MemberConnectionService connectionService;
    private final AuthTokenProvider authTokenProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
      return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() throws Exception {
      return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public CustomAuthorizationFilter customAuthorizationFilter() throws Exception {
      CustomAuthorizationFilter filter = new CustomAuthorizationFilter();
      filter.setAuthenticationEntryPoint(customAuthenticationEntryPoint());
      filter.setMemberConnectionService(connectionService);
      filter.setAuthTokenProvider(authTokenProvider);
      return filter;
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
      CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
      filter.setAuthenticationManager(authenticationManagerBean());
      filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
      filter.setAuthenticationFailureHandler(authenticationFailureHandler);
      filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", HttpMethod.POST.name()));
      filter.setObjectMapper(objectMapper);
      return filter;
    }

    @Bean
    public RefreshTokenFilter refreshTokenFilter() throws Exception {
      RefreshTokenFilter filter = new RefreshTokenFilter();
      filter.setAuthenticationManager(authenticationManagerBean());
      filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
      filter.setAuthenticationFailureHandler(authenticationFailureHandler);
      filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/token", HttpMethod.POST.name()));
      filter.setConnectionService(connectionService);
      filter.setAuthTokenProvider(authTokenProvider);
      filter.setObjectMapper(objectMapper);
      return filter;
    }

    /**
     * ?????? API ?????? ??????.
     * Token ?????? ??????.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
          .csrf().disable()
          .authorizeRequests()
          .mvcMatchers(HttpMethod.POST, "/posts/**").hasAnyRole(MemberRole.ADMIN.name())
          .mvcMatchers(HttpMethod.DELETE, "/posts/**").hasAnyRole(MemberRole.ADMIN.name())
          .mvcMatchers(HttpMethod.PUT, "/posts/**").hasAnyRole(MemberRole.ADMIN.name())
          //.mvcMatchers("/auth").authenticated()
          .anyRequest().permitAll()
          .and()
          .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
          .addFilterBefore(refreshTokenFilter(), UsernamePasswordAuthenticationFilter.class)
          .addFilterBefore(customAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
          .logout()
          .logoutUrl("/auth/logout")
          .logoutSuccessHandler(logoutSuccessHandler)
          .and()
          .exceptionHandling()
          .authenticationEntryPoint(customAuthenticationEntryPoint())
          .accessDeniedHandler(customAccessDeniedHandler())
          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

  }

}
