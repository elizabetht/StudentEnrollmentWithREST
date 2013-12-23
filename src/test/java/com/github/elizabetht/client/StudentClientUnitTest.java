package com.github.elizabetht.client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.mvc.Viewable;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentClientUnitTest {
	private Mockery context;
	private StudentClient studentClient;
	private WebTarget target;
	private Builder builder;

	@Before
	public void beforeEachTest() {
		context = new Mockery();
		studentClient = new StudentClient(
				ClientBuilder
						.newClient()
						.target("http://localhost:8080/StudentEnrollmentWithREST/webapi/studentResource/"));
		target = context.mock(WebTarget.class);
		builder = context.mock(Builder.class);
	}

	@Test
	public void getSignupTest() {
		studentClient = new StudentClient(target);

		final Response response = Response.ok(new Viewable("/signup")).build();
		context.checking(new Expectations() {
			{
				oneOf(target).path(with(any(String.class)));
				will(returnValue(target));
				oneOf(target).request();
				will(returnValue(builder));
				oneOf(builder).get(Response.class);
				will(returnValue(response));
			}
		});
		studentClient.getSignup();
		assertEquals(response.getStatus(), Status.OK.getStatusCode());

		context.assertIsSatisfied();
	}

	@Test
	public void postSignupTest() throws Exception {
		String userName = "jersey";
		String password = "jersey";
		String firstName = "jersey";
		String lastName = "jersey";
		String dateOfBirth = "12-21-2013";
		String emailAddress = "jersey@gmail.com";
		studentClient = new StudentClient(target);

		final Response response = Response.ok(new Viewable("/success")).build();
		context.checking(new Expectations() {
			{
				oneOf(target).path(with(any(String.class)));
				will(returnValue(target));
				oneOf(target).request();
				will(returnValue(builder));
				oneOf(builder).post(with(any(Entity.class)));
				will(returnValue(response));
			}
		});
		studentClient.postSignup(userName, password, firstName, lastName,
				dateOfBirth, emailAddress);
		assertEquals(response.getStatus(), Status.OK.getStatusCode());

		context.assertIsSatisfied();
	}

	@Test(expected = Exception.class)
	public void postSignupInvalidDateFormatTest() throws Exception {
		String userName = "jersey";
		String password = "jersey";
		String firstName = "jersey";
		String lastName = "jersey";
		String dateOfBirth = "12/21/2013";
		String emailAddress = "jersey@gmail.com";
		studentClient = new StudentClient(target);

		final Response response = Response.status(Status.PRECONDITION_FAILED)
				.build();
		context.checking(new Expectations() {
			{
				oneOf(target).path(with(any(String.class)));
				will(returnValue(target));
				oneOf(target).request();
				will(returnValue(builder));
				oneOf(builder).post(with(any(Entity.class)));
				will(returnValue(response));
			}
		});
		studentClient.postSignup(userName, password, firstName, lastName,
				dateOfBirth, emailAddress);
		assertEquals(response.getStatus(), Status.PRECONDITION_FAILED.getStatusCode());

		context.assertIsSatisfied();
	}

	@Test(expected = Exception.class)
	public void postSignupBadRequestTest() throws Exception {
		String userName = null;
		String password = null;
		String firstName = null;
		String lastName = null;
		String dateOfBirth = null;
		String emailAddress = null;
		studentClient = new StudentClient(target);

		final Response response = Response.status(Status.PRECONDITION_FAILED)
				.build();
		context.checking(new Expectations() {
			{
				oneOf(target).path(with(any(String.class)));
				will(returnValue(target));
				oneOf(target).request();
				will(returnValue(builder));
				oneOf(builder).post(with(any(Entity.class)));
				will(returnValue(response));
			}
		});
		studentClient.postSignup(userName, password, firstName, lastName,
				dateOfBirth, emailAddress);
		assertEquals(response.getStatus(), Status.PRECONDITION_FAILED.getStatusCode());

		context.assertIsSatisfied();
	}

	@Test(expected = RuntimeException.class)
	public void postSignupExistingUserTest() throws Exception {
		String userName = "jersey";
		String password = "jersey";
		String firstName = "jersey";
		String lastName = "jersey";
		String dateOfBirth = "12/21/2013";
		String emailAddress = "jersey@gmail.com";
		studentClient = new StudentClient(target);

		final Response response = Response.status(Status.BAD_REQUEST).build();
		context.checking(new Expectations() {
			{
				oneOf(target).path(with(any(String.class)));
				will(returnValue(target));
				oneOf(target).request();
				will(returnValue(builder));
				oneOf(builder).post(with(any(Entity.class)));
				will(returnValue(response));
			}
		});
		studentClient.postSignup(userName, password, firstName, lastName,
				dateOfBirth, emailAddress);
		assertEquals(response.getStatus(), Status.BAD_REQUEST.getStatusCode());

		context.assertIsSatisfied();
	}

	@Test
	public void getLoginTest() {
		studentClient = new StudentClient(target);

		final Response response = Response.ok(new Viewable("/login")).build();
		context.checking(new Expectations() {
			{
				oneOf(target).path(with(any(String.class)));
				will(returnValue(target));
				oneOf(target).request();
				will(returnValue(builder));
				oneOf(builder).get(Response.class);
				will(returnValue(response));
			}
		});
		studentClient.getLogin();
		assertEquals(response.getStatus(), Status.OK.getStatusCode());

		context.assertIsSatisfied();
	}

	@Test
	public void postLoginTest() {
		String userName = "jersey";
		String password = "jersey";
		studentClient = new StudentClient(target);

		final Response response = Response.ok(new Viewable("/success")).build();
		context.checking(new Expectations() {
			{
				oneOf(target).path(with(any(String.class)));
				will(returnValue(target));
				oneOf(target).request();
				will(returnValue(builder));
				oneOf(builder).post(with(any(Entity.class)));
				will(returnValue(response));
			}
		});
		studentClient.postLogin(userName, password);
		assertEquals(response.getStatus(), Status.OK.getStatusCode());

		context.assertIsSatisfied();
	}
	
	@Test(expected=RuntimeException.class)
	public void postLoginInvalidTest() {
		String userName = "jersey";
		String password = "jersey123";
		studentClient = new StudentClient(target);

		final Response response = Response.status(Status.BAD_REQUEST).build();
		context.checking(new Expectations() {
			{
				oneOf(target).path(with(any(String.class)));
				will(returnValue(target));
				oneOf(target).request();
				will(returnValue(builder));
				oneOf(builder).post(with(any(Entity.class)));
				will(returnValue(response));
			}
		});
		studentClient.postLogin(userName, password);
		assertEquals(response.getStatus(), Status.BAD_REQUEST.getStatusCode());

		context.assertIsSatisfied();
	}
	
	@Test(expected=RuntimeException.class)
	public void postLoginBadRequestTest() {
		String userName = null;
		String password = null;
		studentClient = new StudentClient(target);

		final Response response = Response.status(Status.PRECONDITION_FAILED).build();
		context.checking(new Expectations() {
			{
				oneOf(target).path(with(any(String.class)));
				will(returnValue(target));
				oneOf(target).request();
				will(returnValue(builder));
				oneOf(builder).post(with(any(Entity.class)));
				will(returnValue(response));
			}
		});
		studentClient.postLogin(userName, password);
		assertEquals(response.getStatus(), Status.PRECONDITION_FAILED.getStatusCode());

		context.assertIsSatisfied();
	}
}
