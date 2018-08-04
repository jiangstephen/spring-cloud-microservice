package com.example.securityjwt.authorization;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.example.securityjwt.BasicSecurityConfiguration;
import com.example.securityjwt.CustomTokenEnhancer;

@Configuration
@SpringBootApplication
@EnableAuthorizationServer
@Import(BasicSecurityConfiguration.class)
@PropertySource("classpath:application-authorization-server.properties")
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2AuthorizationServerConfig.class, args);
	}

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		endpoints.tokenStore(tokenStore)
		.tokenEnhancer(tokenEnhancerChain).accessTokenConverter(accessTokenConverter)
				.authenticationManager(authenticationManager);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception{
		configurer.inMemory().withClient("clientId")
		.secret("{noop}secret").authorizedGrantTypes("password")
		.scopes("read", "write").resourceIds("resourceId");
	}
	
	@Bean
	public TokenEnhancer tokenEnhancer(){
		return new CustomTokenEnhancer();
	}


}