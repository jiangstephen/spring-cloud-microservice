package com.example.securityjwt;

import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.securityjwt.ressource.OAuth2ResourceServerConfig;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuth2ResourceServerConfig.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationIntegrationTest {

	private static Logger LOG = Logger.getLogger(OAuth2ResourceServerConfig.class);
	
	@Autowired
	private JwtTokenStore tokenStore;
	
	@LocalServerPort
	private int port; 
	
	private Map<String,String> getTokenMap(String username, String password){
		String tokenValue = obtainAccessToken("clientId", username, password);
		Map<String, String> tokenMap = new HashMap<>();
		tokenMap.put("Authorization", "Bearer " + tokenValue );
		return tokenMap;
	}
	
	@Test
	public void whenTokenDoesNotContainIssuer_thenSuccess() {
		String tokenValue = obtainAccessToken("clientId", "admin", "admin");
		LOG.info("The token is " + tokenValue);
		OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
		LOG.info("The authentication token is " + auth);
	}
	
	@Test
	public void test_resourceServiceCity_withoutToken_fail(){
		Response response = RestAssured.given().post("http://localhost:"+port+"/springjwt/cities");
		Assert.assertEquals(401, response.getStatusCode());
	}
	
	@Test
	public void test_resourceServiceCity_withAdminToken_success(){
		
		Response response = RestAssured.given().headers(getTokenMap("admin", "admin")).post("http://localhost:"+port+"/springjwt/cities");
		Assert.assertEquals(200, response.getStatusCode());
		LOG.info(response.prettyPrint());
	}
	
	@Test
	public void test_resourceServiceCity_withUserToken_success(){
		Response response = RestAssured.given().headers(getTokenMap("user", "password")).post("http://localhost:"+port+"/springjwt/cities");
		Assert.assertEquals(200, response.getStatusCode());
	}

	
	@Test
	public void test_resourceServiceUser_withUserToken_failedWith403(){
		Response response = RestAssured.given().headers(getTokenMap("user", "password")).post("http://localhost:"+port+"/springjwt/users");
		Assert.assertEquals(403, response.getStatusCode());
	}
	
	@Test
	public void test_resourceServiceUser_withAdminToken_success(){
		Response response = RestAssured.given().headers(getTokenMap("admin", "admin")).post("http://localhost:"+port+"/springjwt/users");
		Assert.assertEquals(200, response.getStatusCode());
	}

	private String obtainAccessToken(String clientId, String username, String password) {
		Map<String, String> params = new HashMap<>();
		params.put("grant_type", "password");
		params.put("client_id", clientId);
		params.put("username", username);
		params.put("password", password);
		Response response = RestAssured.given().auth().preemptive().basic(clientId, "secret").and().with()
				.params(params).when().post("http://localhost:8091/oauth/token");
		LOG.info("The response is " + response.asString());
		return response.jsonPath().getString("access_token");
	}
}