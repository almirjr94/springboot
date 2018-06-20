package br.com.devdojo.javacliente;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.devdojo.model.Student;

public class JavaSpringClienteTest {

	public static void main(String[] args) {

		RestTemplate restTemplate = new RestTemplateBuilder()
				.rootUri("http://localhost:8080/v1/protected/students")
				.basicAuthorization("almirjr94", "12345").build();
		
		Student student = restTemplate.getForObject("/{id}", Student.class,10);
		System.err.println(student.toString());
		
		ResponseEntity<Student> entity = restTemplate.getForEntity("/{id}", Student.class,10);
		System.out.println(entity.getBody());

		
		Student[] students = restTemplate.getForObject("/", Student[].class,10);
		System.err.println(Arrays.toString(students));
		
		ResponseEntity<List<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Student>>() {});
		
		System.out.println(exchange.getBody());
	}
	


}
