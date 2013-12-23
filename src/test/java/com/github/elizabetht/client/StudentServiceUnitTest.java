package com.github.elizabetht.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.github.elizabetht.model.StudentInterface;
import com.github.elizabetht.repository.StudentRepository;
import com.github.elizabetht.service.StudentService;
import com.github.elizabetht.service.StudentServiceImpl;

public class StudentServiceUnitTest {
	private Mockery context;
	private StudentRepository studentRepository;
	private StudentService studentService;

	@Before
	public void beforeEachTest() throws Exception {
		context = new Mockery();
		studentRepository = context.mock(StudentRepository.class);
		studentService = new StudentServiceImpl();

		Field field = studentService.getClass().getDeclaredField(
				"studentRepository");
		field.setAccessible(true);
		field.set(studentService, studentRepository);
	}

	@Test
	public void findByLoginTest() {
		final String userName = "j2eee";
		final String password = "j2ee";
		final StudentInterface studentInterface = null;

		context.checking(new Expectations() {
			{
				oneOf(studentRepository).findByUserName(userName);
				will(returnValue(studentInterface));
			}
		});
		studentService.findByLogin(userName, password);
		assertNull(studentInterface);

		final String userName1 = "j2ee";
		final StudentInterface studentInterface1 = context
				.mock(StudentInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(studentRepository).findByUserName(userName1);
				will(returnValue(studentInterface1));
				oneOf(studentInterface1).getPassword();
				will(returnValue(password));
			}
		});
		studentService.findByLogin(userName1, password);
		assertNotNull(studentInterface1);
		assertEquals("j2ee", password);

		final String password1 = "j2eee";

		context.checking(new Expectations() {
			{
				oneOf(studentRepository).findByUserName(userName1);
				will(returnValue(studentInterface1));
				oneOf(studentInterface1).getPassword();
				will(returnValue(password1));
			}
		});
		studentService.findByLogin(userName1, password1);
		assertNotNull(studentInterface1);
		assertNotEquals("j2ee", password1);

		context.assertIsSatisfied();
	}

	@Test
	public void findByLoginWithNullParametersTest() {
		final String userName = null;
		final String password = null;
		studentService = context.mock(StudentService.class);

		context.checking(new Expectations() {
			{
				oneOf(studentService).findByLogin(with(aNull(String.class)),
						with(aNull(String.class)));
				will(returnValue(false));
			}
		});
		studentService.findByLogin(userName, password);

		final String userName1 = "j2ee";

		context.checking(new Expectations() {
			{
				oneOf(studentService).findByLogin(with(aNonNull(String.class)),
						with(aNull(String.class)));
				will(returnValue(false));
			}
		});
		studentService.findByLogin(userName1, password);

		final String password1 = "j2eee";

		context.checking(new Expectations() {
			{
				oneOf(studentService).findByLogin(with(aNull(String.class)),
						with(aNonNull(String.class)));
				will(returnValue(false));
			}
		});
		studentService.findByLogin(userName, password1);

		context.checking(new Expectations() {
			{
				oneOf(studentService).findByLogin(with(aNonNull(String.class)),
						with(aNonNull(String.class)));
				will(returnValue(false));
			}
		});
		studentService.findByLogin(userName1, password1);

		context.assertIsSatisfied();
	}

	@Test
	public void findByEmptyLoginTest() {
		final String userName = "";
		final String password = "";
		final StudentInterface studentInterface = null;

		context.checking(new Expectations() {
			{
				oneOf(studentRepository).findByUserName(userName);
				will(returnValue(studentInterface));
			}
		});
		studentService.findByLogin(userName, password);
		assertNull(studentInterface);

		context.assertIsSatisfied();
	}

	@Test
	public void findByUserNameTest() {
		final String userName = "j2ee";

		context.checking(new Expectations() {
			{
				oneOf(studentRepository)
						.findByUserName(with(any(String.class)));
				will(returnValue(with(any(StudentInterface.class))));
			}
		});
		studentService.findByUserName(userName);

		context.assertIsSatisfied();
	}

	@Test
	public void findByBadUserNameTest() {
		final String userName = "j2eee";
		final StudentInterface studentInterface = null;

		context.checking(new Expectations() {
			{
				oneOf(studentRepository)
						.findByUserName(with(any(String.class)));
				will(returnValue(studentInterface));
			}
		});
		studentService.findByUserName(userName);
		assertNull(studentInterface);

		final String userName1 = "j2ee";
		final StudentInterface studentInterface1 = context
				.mock(StudentInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(studentRepository).findByUserName(userName1);
				will(returnValue(studentInterface1));
			}
		});
		studentService.findByUserName(userName1);
		assertNotNull(studentInterface1);

		context.assertIsSatisfied();
	}
}
