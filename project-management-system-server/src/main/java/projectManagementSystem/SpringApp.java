package projectManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringApp {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringApp.class);
        application.run(SpringApp.class, args);
    }
}
