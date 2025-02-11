package learnspringframework.helloWorld;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import learnspringframework.game.GameRunner;
import learnspringframework.game.GamingConsole;

public class App03GamingSpringBeans {
	public static void main(String args[])
	{
		
try (//		1. Launch Spring Context
		var context = new AnnotationConfigApplicationContext(GamingConfiguration.class)) 
	{
			
		context.getBean(GamingConsole.class).up();
		context.getBean(GameRunner.class).run();	
			
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
			
		
//		2. Configure things that we want Spring to manage - 
//		HelloWorldConfiguration - @Configuration
//		name - @Bean

		
	}
}
