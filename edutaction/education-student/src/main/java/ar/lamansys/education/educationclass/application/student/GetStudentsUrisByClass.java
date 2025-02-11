package ar.lamansys.education.educationclass.application.student;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetStudentsUrisByClass {
    List<String> run(String className) {
        return List.of(
                "uriStudent1",
                "uriStudent2",
                "uriStudent3"
        );
    }
}
