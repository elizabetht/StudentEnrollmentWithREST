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

public class StudentClientUnitTest {
	private Mockery context;
	private StudentClient studentClient;

	@Before
	public void beforeEachTest() {

		context = new Mockery();
		studentClient = new StudentClient(
				ClientBuilder
						.newClient()
						.target("http://localhost:8080/StudentEnrollmentWithREST/webapi/studentResource/"));
	}

	@Test
	public void getSignupTest() {
		final WebTarget target = context.mock(WebTarget.class);
		final Builder builder = context.mock(Builder.class);
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

		context.assertIsSatisfied();
	}

	@Test
	public void postSignupTest() throws Exception {
		final WebTarget target = context.mock(WebTarget.class);
		final Builder builder = context.mock(Builder.class);

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

		context.assertIsSatisfied();
	}

	@Test(expected = Exception.class)
	public void postSignupInvalidDateFormatTest() throws Exception {
		final WebTarget target = context.mock(WebTarget.class);
		final Builder builder = context.mock(Builder.class);

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

		context.assertIsSatisfied();
	}

	@Test(expected = Exception.class)
	public void postSignupBadRequestTest() throws Exception {
		final WebTarget target = context.mock(WebTarget.class);
		final Builder builder = context.mock(Builder.class);

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

		context.assertIsSatisfied();
	}

	@Test(expected = RuntimeException.class)
	public void postSignupExistingUserTest() throws Exception {
		final WebTarget target = context.mock(WebTarget.class);
		final Builder builder = context.mock(Builder.class);

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

		context.assertIsSatisfied();
	}

	@Test
	public void getLoginTest() {
		final WebTarget target = context.mock(WebTarget.class);
		final Builder builder = context.mock(Builder.class);
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

		context.assertIsSatisfied();
	}

	@Test
	public void postLoginTest() {
		final WebTarget target = context.mock(WebTarget.class);
		final Builder builder = context.mock(Builder.class);

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

		context.assertIsSatisfied();
	}
	
	@Test(expected=RuntimeException.class)
	public void postLoginInvalidTest() {
		final WebTarget target = context.mock(WebTarget.class);
		final Builder builder = context.mock(Builder.class);

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

		context.assertIsSatisfied();
	}
	
	@Test(expected=RuntimeException.class)
	public void postLoginBadRequestTest() {
		final WebTarget target = context.mock(WebTarget.class);
		final Builder builder = context.mock(Builder.class);

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

		context.assertIsSatisfied();
	}
}
