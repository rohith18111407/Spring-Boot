package com.ProductManagement.beststore.security;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurityConfiguration {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,JwtRequestFilter jwtRequestFilter) throws Exception {
		
		// Step-1 All request should be authenticated
		http.authorizeHttpRequests()
				.requestMatchers("/h2-console/**").permitAll()	//permit without authentication, /** denotes anything after /
				.requestMatchers("/authenticate").permitAll()	//permit without authentication
				.anyRequest().authenticated()
			;
		http.headers().frameOptions().sameOrigin();	//H2 uses frames but SecurityFilterChain does not allow frames, so this snippet permits
		
		//Step-2 If a request is not authenticated, a web page is shown by default (Login page),a popup is shown asking for username and password
		http.httpBasic(withDefaults());
		
		//Step-3 Disable CSRF -> so that you can send POST, PUT methods
		http.csrf().disable();
		
		// Session will not be created, filterchain should not manage session, session management is stateless
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		 http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	
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
	
	@Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, DataSource dataSource) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailService(dataSource)).passwordEncoder(bCryptPasswordEncoder);
        return auth.build();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        return new JwtRequestFilter(userDetailsService, jwtUtil);
    }
	
}
