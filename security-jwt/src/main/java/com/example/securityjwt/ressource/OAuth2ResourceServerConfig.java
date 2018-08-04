package com.example.securityjwt.ressource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import com.example.securityjwt.BasicSecurityConfiguration;

@Configuration
@SpringBootApplication
@EnableResourceServer
@Import(BasicSecurityConfiguration.class)
@PropertySource("classpath:application-resource-server.properties")
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	public static void main(String args[]){
		SpringApplication.run(OAuth2ResourceServerConfig.class, args);
	}
	
	@Autowired
	private ResourceServerTokenServices tokenServices;
	

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.resourceId("resourceId").tokenServices(tokenServices);
    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
    	http.requestMatchers()
    	.and().authorizeRequests().antMatchers("/actuator/**").permitAll()
    	.and().authorizeRequests().antMatchers("/springjwt/**").authenticated();
    }
 
}