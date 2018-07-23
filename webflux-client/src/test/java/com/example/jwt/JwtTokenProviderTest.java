package com.example.jwt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.util.FieldUtils;

import com.sun.security.auth.UserPrincipal;

@RunWith(MockitoJUnitRunner.class)
public class JwtTokenProviderTest {
	
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

	
	private JwtTokenProvider testee;
	
	@Mock
	private Authentication authentication;
	
	private UserPrincipal userPrincipal;
	
	@Before
	public void setUp(){
		userPrincipal = new UserPrincipal("Stephen");
		testee = new JwtTokenProvider();
		FieldUtils.setProtectedFieldValue("jwtSecret", testee, "YWJjZGVm");
		FieldUtils.setProtectedFieldValue("jwtExpirationInMs", testee, 3*1000);
	}
	
	@Test
	public void test_() throws InterruptedException{
		Mockito.when(authentication.getPrincipal()).thenReturn(userPrincipal);
		String token = testee.generateToken(authentication);
		System.out.println(token);
		Assert.assertEquals("Stephen", testee.getUserNameFromJWT(token));
		Assert.assertTrue(testee.validateToken(token));
		Thread.sleep(4000);
		Assert.assertFalse(testee.validateToken(token));
	}

}
