package ar.lamansys.healt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ar.lamansys.healt")
public class HealtApp {
    public static void main(String[] args) {
        SpringApplication.run(HealtApp.class, args);
    }
}


