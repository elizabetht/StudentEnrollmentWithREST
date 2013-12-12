package com.github.elizabetht.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.elizabetht.model.Student;
import com.github.elizabetht.service.StudentService;

@Component
@Path("studentResource")
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
	@Produces(MediaType.TEXT_PLAIN)
	public String signup() {				
		return "signup";
	}
		
	@POST
	@Path("signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String signup(Student student) {		
		if(studentService.findByUserName(student.getUserName())) {			
			return "signup";
		} else {
			studentService.save(student);			
			return "redirect:login.html";
		}
	}
}
