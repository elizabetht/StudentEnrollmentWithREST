package com.github.elizabetht.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.elizabetht.model.Student;
import com.github.elizabetht.service.StudentService;

@Component
@Path("studentResource")
@XmlRootElement
public class StudentResource {
	
	@Autowired
	private StudentService studentService;
	
	@GET	
	@Produces(MediaType.TEXT_PLAIN)
	public String start() {
		if(studentService != null) {
			return "StudentService: Not Null";
		} else {
			return "StudentService: Null";
		}
	}
	
	@GET
	@Path("signup")
	@Produces(MediaType.TEXT_HTML)
	public Viewable signup() {			
		return new Viewable("/signup");
	}
		
	@POST
	@Path("signup")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Viewable signup(@FormParam("userName") String userName,
			@FormParam("password") String password,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("dateOfBirth") String dateOfBirth,
			@FormParam("emailAddress") String emailAddress) throws ParseException {
		
		Student student = new Student();
		student.setUserName(userName);
		student.setPassword(password);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setDateOfBirth(new java.sql.Date(new SimpleDateFormat("MM/dd/yyyy")
			.parse(dateOfBirth.substring(0, 10)).getTime()));
		student.setEmailAddress(emailAddress);
		
		if(studentService.findByUserName(userName)) {
			Map<String, Object> map = new HashMap<String, Object>();
	        map.put("message", "User Name exists. Try another user name");
	        map.put("student", student);
			return new Viewable("/signup", map);
			
		} else {
			studentService.save(student);
			return new Viewable("/login");
		}
	}
	
	@GET
	@Path("login")
	@Produces(MediaType.TEXT_HTML)
	public Viewable login() {			
		return new Viewable("/login");
	}
	
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Viewable login(@FormParam("userName") String userName,
			@FormParam("password") String password) {
		boolean found = studentService.findByLogin(userName, password);
		if (found) {				
			return new Viewable("/success");
		} else {				
			return new Viewable("/failure");
		}
	}
	
	
}
