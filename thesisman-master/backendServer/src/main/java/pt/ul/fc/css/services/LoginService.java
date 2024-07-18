package pt.ul.fc.css.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.repositories.StudentRepository;
import pt.ul.fc.css.repositories.TeacherRepository;
import pt.ul.fc.css.entities.Student;
import pt.ul.fc.css.entities.Teacher;

@Service
public class LoginService {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;

    public StudentDTO login(String username) {
        // Retrieve student from the database by username
        Optional<Student> studentOptional = studentRepository.findOptionalByUsername(username);
        
        // If student is found, convert it to StudentDTO and return
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return convertToDto(student);
        }
        
        // If student is not found, return null or throw an exception
        return null;
    }
    
    public TeacherDTO loginTeacher(String username) {
        // Retrieve teacher from the database by username
        Optional<Teacher> teacherOptional = teacherRepository.findOptionalByUsername(username);

        // If teacher is found, convert it to TeacherDTO and return
        if (teacherOptional.isPresent()) {
            Teacher teacher = teacherOptional.get();
            return Dtofy(teacher);
        }

        // If teacher is not found, return null or throw an exception
        return null;
    }


    // Convert Student entity to StudentDTO
    private StudentDTO convertToDto(Student student) {
        StudentDTO studentDTO = new StudentDTO(null, 0, null);
        studentDTO.setId(student.getId());
        // Add other properties specific to StudentDTO
        studentDTO.setName(student.getName());
        studentDTO.setAverageGrade(student.getAverageGrade());
        // Add other properties as needed
        return studentDTO;
    }
    
    private TeacherDTO Dtofy(Teacher teacher) {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(teacher.getId());
        teacherDTO.setName(teacher.getName());
        teacherDTO.setUsername(teacher.getUsername());
        teacherDTO.setPassword(teacher.getPassword());
        // You may need to map other properties as well based on your requirements
        return teacherDTO;
    }

}
