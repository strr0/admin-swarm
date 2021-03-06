package com.strr.adminserver.config;

import com.strr.adminserver.service.SysUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final AuthenticationManager authenticationManager;
    private final SysUserDetailsService sysUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationServerConfig(AuthenticationManager authenticationManager,
                                     SysUserDetailsService sysUserDetailsService,
                                     PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.sysUserDetailsService = sysUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("STRR_CLIENT")
                .secret(passwordEncoder.encode("STRR_SECRET"))
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                .scopes("web");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(sysUserDetailsService);
    }
}
