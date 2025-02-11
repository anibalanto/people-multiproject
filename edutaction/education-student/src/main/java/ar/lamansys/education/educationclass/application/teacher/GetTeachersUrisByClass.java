package ar.lamansys.education.educationclass.application.teacher;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetTeachersUrisByClass {
    List<String> run(String className) {
        return List.of(
                "uriTeacher1",
                "uriTeacher2"
        );
    }
}
