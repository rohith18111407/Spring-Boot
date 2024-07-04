package com.telusko.demo;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.telusko.demo.game.GameRunner;
import com.telusko.demo.game.MarioGame;
import com.telusko.demo.game.PacManGame;
import com.telusko.demo.game.SuperContraGame;

public class App02HelloWorldSpring {

	public static void main(String[] args)
	{
		//1. Launch a spring context
		var context=new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);		
		
		//2. Configure the things that we want Spring to manage - @Configuration
		//HelloWorldConfiguration - @Configuration
		//name - @Bean
		
		//3. Retrieve Beans managed by Spring 
		
		System.out.println(context.getBean("name"));
		System.out.println(context.getBean("age"));
		System.out.println(context.getBean("person"));
		System.out.println(context.getBean("person2MethodCall"));
		System.out.println(context.getBean("person3Parameters"));
		System.out.println(context.getBean("address2"));
//		System.out.println(context.getBean(Address.class));
		
		Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
	}
}
