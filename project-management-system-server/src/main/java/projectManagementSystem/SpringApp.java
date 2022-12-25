package projectManagementSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import projectManagementSystem.controller.request.FilterRequest;
import projectManagementSystem.service.FilterCriteriaService;

@SpringBootApplication
public class SpringApp {
    @Autowired
    FilterCriteriaService filterCriteriaService;


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringApp.class);
        application.run(SpringApp.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    void filterBoard(){
    }
}
