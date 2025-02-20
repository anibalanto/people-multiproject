package ar.lamansys.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ar.lamansys.health")
public class HealthApp {
    public static void main(String[] args) {
        SpringApplication.run(HealthApp.class, args);
    }
}


