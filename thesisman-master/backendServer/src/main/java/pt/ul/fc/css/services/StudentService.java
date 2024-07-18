package pt.ul.fc.css.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.entities.Student;
import pt.ul.fc.css.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    
    public Student findById(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.orElse(null);
    }

    
    public void save(Student student) {
        studentRepository.save(student);
    }

    
    public Optional<Student> findOptionalByUsername(String username) {
        return studentRepository.findOptionalByUsername(username);
    }

    
    public StudentDTO convertToDto(Student student) {
        if (student == null) {
            return null;
        }
        StudentDTO studentDTO = new StudentDTO(null, 0, null);
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        // Add any other necessary fields
        return studentDTO;
    }
}
