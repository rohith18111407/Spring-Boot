package learnspringframework.helloWorld;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


record Person(String name,int age,Address address) {};
record Address(String firstLine,String city) {};

@Configuration
public class HelloWorldConfiguration {
	
	@Bean
	public String name() {
		return "Rohith";
	}
	
	@Bean
	public int age() {
		return 21;
	}
	
	@Bean
	public Person person() {
		return new Person("Krithick",24,new Address("Bangalore","Karnataka"));
	}
	
	//Use of existing Beans like name bean, age bean, address bean to create a bean
	@Bean
	public Person person2MethodCall() {
		return new Person(name(),age(),address());
	}
	
	//Use of Parameters to create a Bean
	@Bean
	public Person person3Parameters(String name,int age,Address address2) { //Parameters: name, age, address2
		return new Person(name,age,address2);
	}
	
	//Use of Parameters to create a Bean
	@Bean
	public Person person4Parameters(String name,int age,Address address) { 
		return new Person(name,age,address);
	}	
	
	@Bean
	public Address address() {
		return new Address("Bangalore","Karnataka");
	}
	
	@Bean(name="address2")
	public Address address1() {
		return new Address("Chennai","Tamil Nadu");
	}
}
