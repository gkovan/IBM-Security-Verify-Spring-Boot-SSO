package com.gkovan.resource.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {// @formatter:off
       
    	http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
        .cors()
            .and()
              .authorizeRequests()
                .antMatchers("/open/api/test").permitAll()
                .antMatchers(HttpMethod.GET, "/favorite-foods", "/user/info","/api/foos/**")
                  .hasAuthority("SCOPE_read")
                .antMatchers(HttpMethod.POST, "/api/foos")
                  .hasAuthority("SCOPE_write")
                .anyRequest()
                  .authenticated()
            .and()
              .oauth2ResourceServer()
                .jwt();
    }//@formatter:on
}