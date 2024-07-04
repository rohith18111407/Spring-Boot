package com.in28minutes.rest.webservices.restful_web_services.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restful_web_services.PostRepository;
import com.in28minutes.rest.webservices.restful_web_services.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	@Autowired
//	private UserDaoService service;
	
	private UserRepository userRepository;
	private PostRepository postRepository;
	
	public UserJpaResource( UserRepository userRepository,PostRepository postRepository) {
//		this.service = service;
		this.userRepository = userRepository;
		this.postRepository=postRepository;
	}

	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public Optional<User> retrieveAllUser(@PathVariable int id){
		Optional<User> user=userRepository.findById(id);
		if(user.isEmpty())
			throw new UserNotFoundException("id: "+id);
//		EntityModel<User> entityModel=EntityModel.of(user.get());
		
		return user;
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){
		userRepository.deleteById(id);
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser=userRepository.save(user);
		URI location=ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id){
		Optional<User> user=userRepository.findById(id);
		if(user.isEmpty())
			throw new UserNotFoundException("id: "+id);
//		EntityModel<User> entityModel=EntityModel.of(user.get()); 
		
		return user.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public List<Post> createPostsForUser(@PathVariable int id,@Valid @RequestBody Post post){
		Optional<User> user=userRepository.findById(id);
		if(user.isEmpty())
			throw new UserNotFoundException("id: "+id);
//		EntityModel<User> entityModel=EntityModel.of(user.get()); 
		
		post.setUser(user.get());
		
		postRepository.save(post);
		
		return user.get().getPosts();
	}
}
