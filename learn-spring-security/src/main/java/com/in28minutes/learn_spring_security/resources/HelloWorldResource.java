package com.in28minutes.learn_spring_security.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

record Todo(String username,String description) {};

@RestController
public class HelloWorldResource {
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	private static final List<Todo> TODOS_LIST=
			List.of(new Todo("Rohith","Get AWS Certified"),
					new Todo("Rohith","Learn SpringBoot"));
	
	@GetMapping("/todos")
	public List<Todo> retirieveAllTodos()
	{
		return TODOS_LIST;
	}
	
	@GetMapping("/users/{username}/todos")
	public Todo retrieveTodosForSpecificUser(@PathVariable String username){
		return 	TODOS_LIST.get(0);
	}
	
	
	@PostMapping("/users/{username}/todos")
	public void createTodoForSpecificUser(@PathVariable String username,@RequestBody Todo todo){
		logger.info("Creating {} for {}",todo,username);
	}
}
