package pt.ul.fc.css.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.services.TeacherDTO;
import pt.ul.fc.css.entities.Teacher;
import pt.ul.fc.css.repositories.TeacherRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public TeacherDTO convertToDto(Teacher teacher) {
        List<Long> thesisIds = teacher.getTheses().stream()
            .map(thesis -> thesis.getId())
            .collect(Collectors.toList());

        return new TeacherDTO(
                teacher.getId(),
                teacher.getName(),
                teacher.getUsername(),
                teacher.getPassword(),
                thesisIds,
                teacher.isAdmin()
        );
    }

    public boolean isAdmin(Long teacherId) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        return teacherOptional.map(Teacher::isAdmin).orElse(false);
    }

	public List<Teacher> getAllTeacher() {
		return teacherRepository.findAll();
	}
    
    
}
