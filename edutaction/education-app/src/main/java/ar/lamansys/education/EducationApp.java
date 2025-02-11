package ar.lamansys.education;

import ar.lamansys.people.core.domain.PersonDV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ar.lamansys.education")
public class EducationApp {
    public static void main(String[] args) {
        SpringApplication.run(EducationApp.class, args);
    }
}


