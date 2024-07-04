package com.in28minutes.learn_spring_security.basic;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


// Method-4 disable BasicAuthSecurity configuration (none of the below Bean will be executed)
//@Configuration
public class BasicAuthSecurityConfiguration {
	
	@Bean
	SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(
				auth->{
					auth.anyRequest().authenticated();
		});
		
		http.sessionManagement(
					session->
					session.sessionCreationPolicy(
							SessionCreationPolicy.STATELESS)
				);
		//http.formLogin();
		http.httpBasic();
		
		http.csrf().disable();
		
		http.headers().frameOptions().sameOrigin();
		
		return http.build();
	}
	
	
	
	// --------------------------------------- Method-1 In Memory User Details Manager ------------------------------------
	
//	@Bean
//	public UserDetailsService userDetailService() {
//		
//		var user=User.withUsername("Rohith")
//			.password("{noop}pass")
//			.roles("USER")
//			.build();
//		
//		var admin=User.withUsername("admin")
//				.password("{noop}pass")
//				.roles("ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user,admin);
//	}
	
	
	
	
	
	
	
	//------------------------------- Method-2 Storing User Credentials using JDBC -------------------------------
	
//	@Bean
//	public DataSource dataSource() {
//		return new EmbeddedDatabaseBuilder()
//				.setType(EmbeddedDatabaseType.H2)
//				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//				.build();
//	}
//	
//	@Bean
//	public UserDetailsService userDetailService(DataSource dataSource) {
//		
//		var user=User.withUsername("Rohith")
//			.password("{noop}pass")
//			.roles("USER")
//			.build();
//		
//		var admin=User.withUsername("admin")
//				.password("{noop}pass")
//				.roles("ADMIN","USER")
//				.build();
//		
//		var jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);
//		jdbcUserDetailsManager.createUser(user);
//		jdbcUserDetailsManager.createUser(admin);
//		
//		return jdbcUserDetailsManager;
//		
//	}
	
	
	
	
	
	
	//------------------------------- Method-3 Storing User Credentials using BCrypt Password Encoder -------------------------------
	
	
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
	}
	
	@Bean
	public UserDetailsService userDetailService(DataSource dataSource) {
		
		var user=User.withUsername("Rohith")
			.password("pass")
			.passwordEncoder(str->passwordEncoder().encode(str))
			.roles("USER")
			.build();
		
		var admin=User.withUsername("admin")
				.password("pass")
				.passwordEncoder(str->passwordEncoder().encode(str))
				.roles("ADMIN","USER")
				.build();
		
		var jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.createUser(user);
		jdbcUserDetailsManager.createUser(admin);
		
		return jdbcUserDetailsManager;
		
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
