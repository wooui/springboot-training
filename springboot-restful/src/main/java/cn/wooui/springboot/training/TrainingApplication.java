package cn.wooui.springboot.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TrainingApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TrainingApplication.class, args);
		System.out.println("Total " + context.getBeanDefinitionCount());
	}

	@RequestMapping("/")
	public String index() {
		return "Hello Spring Boot";
	}
}
