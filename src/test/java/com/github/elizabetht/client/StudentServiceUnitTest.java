package com.github.elizabetht.client;

import java.lang.reflect.Field;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.github.elizabetht.model.StudentInterface;
import com.github.elizabetht.repository.StudentRepository;
import com.github.elizabetht.service.StudentServiceImpl;

public class StudentServiceUnitTest {
	private Mockery context;
	private StudentRepository studentRepository;
	private StudentServiceImpl studentService;
	
	@Before
	public void beforeEachTest() throws Exception {
		context = new Mockery();
		studentRepository = context.mock(StudentRepository.class);
		studentService = new StudentServiceImpl();
		
		Field field = studentService.getClass().getDeclaredField("studentRepository");
		field.setAccessible(true);
		field.set(studentService, studentRepository);
	}
		
	@Test
	public void findByLoginTest() {			
		final String userName = "j2eee";
		final String password = "j2ee";
		final StudentInterface studentInterface = null;
		
		context.checking(new Expectations() {{
			oneOf(studentRepository).findByUserName(userName);
			will(returnValue(studentInterface));	
		}});
		studentService.findByLogin(userName, password);
				
		final String userName1 = "j2ee";
		final StudentInterface studentInterface1 = context.mock(StudentInterface.class);
		
		context.checking(new Expectations() {{
			oneOf(studentRepository).findByUserName(userName1);
			will(returnValue(studentInterface1));
			oneOf(studentInterface1).getPassword();
			will(returnValue(password));
		}});
		studentService.findByLogin(userName1, password);
				
		final String password1 = "j2eee";
		
		context.checking(new Expectations() {{
			oneOf(studentRepository).findByUserName(userName1);
			will(returnValue(studentInterface1));
			oneOf(studentInterface1).getPassword();
			will(returnValue(password1));
		}});
		studentService.findByLogin(userName1, password1);
				
		context.assertIsSatisfied();		
	}
	
	@Test
	public void findByEmptyLoginTest() {		
		final String userName = "";
		final String password = "";
		final StudentInterface studentInterface = null;
		
		context.checking(new Expectations() {{
			oneOf(studentRepository).findByUserName(userName);
			will(returnValue(studentInterface));	
		}});
		studentService.findByLogin(userName, password);
				
		context.assertIsSatisfied();		
	}
	
	@Test
	public void findByUserNameTest() {
		final String userName = "j2ee";
		
		context.checking(new Expectations() {{
			oneOf(studentRepository).findByUserName(with(any(String.class)));
			will(returnValue(with(any(StudentInterface.class))));	
		}});
		studentService.findByUserName(userName);
		
		context.assertIsSatisfied();			
	}
	
	@Test
	public void findByBadUserNameTest() {
		final String userName = "j2eee";
		final StudentInterface studentInterface = null;
		
		context.checking(new Expectations() {{
			oneOf(studentRepository).findByUserName(with(any(String.class)));
			will(returnValue(studentInterface));	
		}});
		studentService.findByUserName(userName);
		
		final String userName1 = "j2ee";
		final StudentInterface studentInterface1 = context.mock(StudentInterface.class);
		
		context.checking(new Expectations() {{
			oneOf(studentRepository).findByUserName(userName1);
			will(returnValue(studentInterface1));
		}});
		studentService.findByUserName(userName1);
				
		context.assertIsSatisfied();			
	}	
}
