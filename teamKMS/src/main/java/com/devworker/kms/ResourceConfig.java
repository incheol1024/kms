package com.devworker.kms;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsUtils;

@Configuration
public class ResourceConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors();

        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/oauth/token").permitAll()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .requestMatchers(CorsUtils::isCorsRequest).authenticated()
                .antMatchers("/**").authenticated();

        http.csrf().disable();
    }


}
