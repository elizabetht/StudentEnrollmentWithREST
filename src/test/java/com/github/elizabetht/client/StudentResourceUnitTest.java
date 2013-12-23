package com.github.elizabetht.client;

import java.lang.reflect.Field;
import java.text.ParseException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.github.elizabetht.model.StudentInterface;
import com.github.elizabetht.resource.StudentResource;
import com.github.elizabetht.resource.StudentResourceInterface;
import com.github.elizabetht.service.StudentService;

import static org.junit.Assert.*;

public class StudentResourceUnitTest {
	private Mockery context;
	private StudentService studentService;
	private StudentResourceInterface studentResourceInterface;
	
	@Before
	public void beforeEachTest() throws Exception {
		context = new Mockery();
		studentService = context.mock(StudentService.class);
		studentResourceInterface = new StudentResource();
		
		Field field = studentResourceInterface.getClass().getDeclaredField("studentService");
		field.setAccessible(true);
		field.set(studentResourceInterface, studentService);
	}
		
	@Test
	public void postSignupResourceTest() throws ParseException {	
		final String userName = "jersey";
		String password = "jersey";
		String firstName = "jersey";
		String lastName = "jersey";
		String dateOfBirth = "12/21/2013";
		String emailAddress = "jersey@gmail.com";
		final StudentInterface student = context.mock(StudentInterface.class);
									
		context.checking(new Expectations() {{
			oneOf(studentService).findByUserName(userName);
			will(returnValue(false));
			oneOf(studentService).save(with(any(StudentInterface.class)));
			will(returnValue(student));			
		}});
		studentResourceInterface.signup(userName, password, firstName, lastName, dateOfBirth, emailAddress);
		
		context.assertIsSatisfied();		
	}
	
	@Test
	public void postSignupResourceForExistingUserTest() throws ParseException {	
		final String userName = "jersey";
		String password = "jersey";
		String firstName = "jersey";
		String lastName = "jersey";
		String dateOfBirth = "12/21/2013";
		String emailAddress = "jersey@gmail.com";
											
		context.checking(new Expectations() {{
			oneOf(studentService).findByUserName(userName);
			will(returnValue(true));			
		}});
		studentResourceInterface.signup(userName, password, firstName, lastName, dateOfBirth, emailAddress);
		
		context.assertIsSatisfied();		
	}
	
	@Test
	public void postSignupResourceForBadRequestTest() throws ParseException {	
		String userName = null;
		String password = null;
		String firstName = null;
		String lastName = null;
		String dateOfBirth = null;
		String emailAddress = null;
		final StudentResourceInterface studentResourceInterface = context.mock(StudentResourceInterface.class);
													
		context.checking(new Expectations() {{
			oneOf(studentResourceInterface).signup(with(aNull(String.class)), with(aNull(String.class)), with(aNull(String.class)), with(aNull(String.class)), with(aNull(String.class)), with(aNull(String.class)));
			will(returnValue(with(any(Response.class))));			
		}});
		studentResourceInterface.signup(userName, password, firstName, lastName, dateOfBirth, emailAddress);
		
		userName = "jersey";
		context.checking(new Expectations() {{
			oneOf(studentResourceInterface).signup(with(aNonNull(String.class)), with(aNull(String.class)), with(aNull(String.class)), with(aNull(String.class)), with(aNull(String.class)), with(aNull(String.class)));
			will(returnValue(with(any(Response.class))));			
		}});
		studentResourceInterface.signup(userName, password, firstName, lastName, dateOfBirth, emailAddress);
		
		password="jersey";
		context.checking(new Expectations() {{
			oneOf(studentResourceInterface).signup(with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNull(String.class)), with(aNull(String.class)), with(aNull(String.class)), with(aNull(String.class)));
			will(returnValue(with(any(Response.class))));			
		}});
		studentResourceInterface.signup(userName, password, firstName, lastName, dateOfBirth, emailAddress);
		
		firstName="jersey";
		context.checking(new Expectations() {{
			oneOf(studentResourceInterface).signup(with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNull(String.class)), with(aNull(String.class)), with(aNull(String.class)));
			will(returnValue(with(any(Response.class))));			
		}});
		studentResourceInterface.signup(userName, password, firstName, lastName, dateOfBirth, emailAddress);
		
		lastName="jersey";
		context.checking(new Expectations() {{
			oneOf(studentResourceInterface).signup(with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNull(String.class)), with(aNull(String.class)));
			will(returnValue(with(any(Response.class))));			
		}});
		studentResourceInterface.signup(userName, password, firstName, lastName, dateOfBirth, emailAddress);
		
		dateOfBirth="12/20/2013";
		context.checking(new Expectations() {{
			oneOf(studentResourceInterface).signup(with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNull(String.class)));
			will(returnValue(with(any(Response.class))));			
		}});
		studentResourceInterface.signup(userName, password, firstName, lastName, dateOfBirth, emailAddress);
		
		emailAddress="jersey@gmail.com";
		context.checking(new Expectations() {{
			oneOf(studentResourceInterface).signup(with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNonNull(String.class)), with(aNonNull(String.class)));
			will(returnValue(with(any(Response.class))));			
		}});
		studentResourceInterface.signup(userName, password, firstName, lastName, dateOfBirth, emailAddress);
		
		
		context.assertIsSatisfied();		
	}
		
	@Test
	public void postLoginResourceTest() throws ParseException {	
		final String userName = "jersey";
		final String password = "jersey";
									
		context.checking(new Expectations() {{
			oneOf(studentService).findByLogin(userName, password);
			will(returnValue(true));			
		}});
		studentResourceInterface.login(userName, password);
		
		final String password1 = "jersey123";
		context.checking(new Expectations() {{
			oneOf(studentService).findByLogin(userName, password1);
			will(returnValue(false));			
		}});
		studentResourceInterface.login(userName, password1);
		
		
		context.assertIsSatisfied();		
	}
	
	@Test
	public void postLoginResourceForBadRequestTest() throws ParseException {	
		final String userName = null;
		final String password = null;
		final StudentResourceInterface studentResourceInterface = context.mock(StudentResourceInterface.class);
		final Response response = Response.status(Status.PRECONDITION_FAILED).build();
		
		context.checking(new Expectations() {{
			oneOf(studentResourceInterface).login(with(aNull(String.class)), with(aNull(String.class)));
			will(returnValue(response));	
		}});
		studentResourceInterface.login(userName, password);
		assertEquals(response.getStatus(), Status.PRECONDITION_FAILED.getStatusCode());
		
		final String userName1="jersey";
		
		context.checking(new Expectations() {{
			oneOf(studentResourceInterface).login(with(aNonNull(String.class)), with(aNull(String.class)));
			will(returnValue(response));
		}});
		studentResourceInterface.login(userName1, password);
		assertEquals(response.getStatus(), Status.PRECONDITION_FAILED.getStatusCode());
		
		final String password1="jersey";
		context.checking(new Expectations() {{
			oneOf(studentResourceInterface).login(with(aNull(String.class)), with(aNonNull(String.class)));
			will(returnValue(response));
		}});
		studentResourceInterface.login(userName, password1);
		assertEquals(response.getStatus(), Status.PRECONDITION_FAILED.getStatusCode());
		
		context.checking(new Expectations(){{
			oneOf(studentResourceInterface).login(with(aNonNull(String.class)), with(aNonNull(String.class)));
			will(returnValue(with(any(Response.class))));
		}});
		studentResourceInterface.login(userName1, password1);
				
		context.assertIsSatisfied();
	}
}
