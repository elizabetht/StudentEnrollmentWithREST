package com.github.elizabetht.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.elizabetht.model.Student;
import com.github.elizabetht.model.StudentInterface;
import com.github.elizabetht.repository.StudentRepository;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Transactional
	public StudentInterface save(StudentInterface student) {
		return studentRepository.save((Student)student);
	}

	public boolean findByLogin(String userName, String password) {	
		StudentInterface stud = studentRepository.findByUserName(userName);
		
		if(stud != null && stud.getPassword().equals(password)) {
			return true;
		} 
		
		return false;		
	}

	public boolean findByUserName(String userName) {
		StudentInterface stud = studentRepository.findByUserName(userName);
		
		if(stud != null) {
			return true;
		}
		
		return false;
	}
}
