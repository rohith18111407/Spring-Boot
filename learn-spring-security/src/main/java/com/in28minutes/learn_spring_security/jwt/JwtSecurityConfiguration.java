package com.in28minutes.learn_spring_security.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class JwtSecurityConfiguration {
	
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
		
		http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
		
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
	
	
	
	//Step-1 Generate Key Pair for JWT Auth with Spring Security and Spring Boot
	@Bean
	public KeyPair keyPair() {
		
		try {
			var keyPairGenerator=KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048); 	//keysize=2048 bits
			return keyPairGenerator.generateKeyPair();
		}catch(Exception ex)
		{
			throw new RuntimeException(ex);
		}
		
	}
	
	
	//Step-2 Create RSA Key Object using Key Pair for JWT Auth with Spring Security and Spring Boot
	@Bean
	public RSAKey rsaKey(KeyPair keyPair) {
		return new RSAKey
				.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey(keyPair.getPrivate())
				.keyID(UUID.randomUUID().toString())
				.build();
	} 
		
	
	//Step-3 Create JWKSource (JSON Web Key Source) for JWT Auth with Spring Security and Spring Boot
	@Bean
	public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
		var jwkSet=new JWKSet(rsaKey);
		
		return (jwkSelector,context)->jwkSelector.select(jwkSet);
	}
		
	
	//Step-4 Use RSA Public Key for Decoding for JWT Auth with Spring Security and Spring Boot
	@Bean
	public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
		return NimbusJwtDecoder
				.withPublicKey(rsaKey.toRSAPublicKey())
				.build();
	}
	
	
	
	//Step-1 Create a JWT(needs Encoding) for setting up JWT Resource
	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource)
	{
		return new NimbusJwtEncoder(jwkSource);
	}
	
}






