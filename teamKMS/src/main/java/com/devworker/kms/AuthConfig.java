package com.devworker.kms;

import com.devworker.kms.component.AuthComponent;
import com.devworker.kms.dto.UserDto;
import com.devworker.kms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
public class AuthConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private UserService service;
    @Autowired
    private AuthComponent auth;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()")
                .tokenKeyAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .authorizedGrantTypes("client_credentials","password", "refresh_token")
                .scopes("read","write","trust")
                .resourceIds("resource")
                .secret("kmsSecret");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.userDetailsService(userName -> {
                    UserDto user = service.getUser(userName);
                    return new KMSUserDetail(user.getId(),user.getPassword());
                })
                .authenticationManager(authenticationManager())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE);
    }

    private AuthenticationManager authenticationManager() {
       return authentication -> {
           UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
           auth.auth(token.getName(), token.getCredentials().toString());
           KMSUserDetail detail = new KMSUserDetail(token.getName(), token.getCredentials().toString());
           return new UsernamePasswordAuthenticationToken(detail.getUsername(), null, detail.getAuthorities());
       };
    }
}
