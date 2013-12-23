package com.github.elizabetht.client;

import javax.ws.rs.client.ClientBuilder;

import org.junit.Before;
import org.junit.Test;

public class StudentClientIntegrationTest {
	private StudentClient client;	
		
	@Before
	public void beforeEachTest() {
		this.client = new StudentClient(ClientBuilder.newClient().target("http://localhost:8080/StudentEnrollmentWithREST/webapi/studentResource/"));
	}
		
	@Test(expected=RuntimeException.class)
	public void PostSignupFailureForExistingUserTest() throws Exception {
		client.postSignup("j2ee", "j2ee", "jersey", "jersey", "12/15/2013", "jersey@gmail.com");	
	}
	
	@Test(expected=Exception.class)
	public void PostSignupInvalidDateTest() throws Exception {
		client.postSignup("jersey", "jersey", "jersey", "jersey", "12-15-2013", "jersey@gmail.com");
	}
	
	@Test(expected=RuntimeException.class)
	public void PostSignupBadRequestTest() throws Exception {
		client.postSignup(null, null, null, null, null, null);
	}
		
	@Test(expected=RuntimeException.class)
	public void PostLoginFailureTest() {
		client.postLogin("jersey", "jersey123");
	}
	
	@Test(expected=RuntimeException.class)
	public void PostLoginBadRequestTest() {
		client.postLogin(null, null);
	}

}
