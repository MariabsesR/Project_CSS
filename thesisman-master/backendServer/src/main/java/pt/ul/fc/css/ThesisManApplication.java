package pt.ul.fc.css;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import pt.ul.fc.css.entities.*;
import pt.ul.fc.css.repositories.*;
import pt.ul.fc.css.services.DocumentService;
import pt.ul.fc.css.services.StorageProperties;

@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class ThesisManApplication {

  private static final Logger log = LoggerFactory.getLogger(ThesisManApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ThesisManApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo(
      StudentRepository studentRepository,
      TeacherRepository teacherRepository,
      RoomRepository roomRepository,
      TimeSlotRepository timeSlotRepository,
      ThesisThemeRepository thesisThemeRepository,
      DocumentRepository documentRepository,
      ThesisDefenceRepository thesisDefenceRepository,
      ThesisProposalRepository thesisProposalRepository,
      ThesisFinalRepository thesisFinalRepository,
      CompanyEmployeeRepository companyEmployeeRepository,
      MasterRepository masterRepository,
      DissertationRepository dissertationRepository,
      ProjectRepository projectRepository,
      ThesisRepository thesisRepository,
      JuryRepository juryRepository,
      DocumentService documentService) {
    return (args) -> {
      documentService.init();
      // save a master
      Master master1 = new Master("LEI", 1231L);
      masterRepository.save(master1);
      // Save a teacher
      Teacher teacher = new Teacher("John Doe", "john", "password", false);
      teacherRepository.save(teacher);

      // Create thesis themes
      ThesisTheme thesisTheme = new ThesisTheme("Project 1", "Description 1", 300.02, teacher);
      ThesisTheme thesisTheme2 = new ThesisTheme("Project 2", "Description 2", 250.50, teacher);
      thesisTheme.addMaster(master1);
      thesisTheme2.addMaster(master1);
      // Save thesis themes
      thesisThemeRepository.saveAll(Arrays.asList(thesisTheme, thesisTheme2));
      teacher.addThesisThemes(thesisTheme);
      teacher.addThesisThemes(thesisTheme2);
      teacherRepository.save(teacher);

      // Save a student
      Student student = new Student("alice", "password", "Alice Smith", 16.7, master1);
      studentRepository.save(student);

      // Add thesis themes to student

      thesisTheme.getInterestedStudents().add(student);
      thesisTheme2.getInterestedStudents().add(student);
      studentRepository.save(student);
      thesisThemeRepository.saveAll(Arrays.asList(thesisTheme, thesisTheme2));

      // Save room and time slots
      LocalDate date = LocalDate.now();
      TimeSlot timeSlot1 = new TimeSlot(LocalTime.of(8, 0), LocalTime.of(10, 0), date);
      TimeSlot timeSlot2 = new TimeSlot(LocalTime.of(14, 0), LocalTime.of(16, 0), date);
      TimeSlot timeSlot3 = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0), date);
      TimeSlot timeSlot4 = new TimeSlot(LocalTime.of(12, 0), LocalTime.of(14, 0), date);
      timeSlotRepository.saveAll(Arrays.asList(timeSlot1, timeSlot2, timeSlot3, timeSlot4));

      Room room = new Room();
      room.addAvailableTimeSlot(timeSlot1);
      room.addAvailableTimeSlot(timeSlot2);
      room.addAvailableTimeSlot(timeSlot3);
      room.addAvailableTimeSlot(timeSlot4);
      roomRepository.save(room);

      // Save a Document entity
      String filepath = ("example_document.pdf");
      Document document = new Document(filepath);
      documentRepository.save(document);

      // Create a ThesisDefence entity and set the associated Document and TimeSlot
      ThesisDefence thesisDefence = new ThesisDefence(true, 60, timeSlot1, document);
      thesisDefence.setRoom(room);
      thesisDefence.setGrade(8.5);
      thesisDefenceRepository.save(thesisDefence);

      // Create a ThesisProposal instance and save it
      ThesisProposal thesisProposal = new ThesisProposal();
      thesisProposal.setThesisDefence(thesisDefence);
      String proposalFile1 = "proposal_document_1.pdf";
      String proposalFile2 = "proposal_document_2.pdf";
      Document proposalDocument1 = new Document(proposalFile1);
      Document proposalDocument2 = new Document(proposalFile2);
      documentRepository.saveAll(Arrays.asList(proposalDocument1, proposalDocument2));

      thesisProposal.setLastDocumentSubmited(proposalDocument1);
      thesisProposalRepository.save(thesisProposal);

      // Create a ThesisFinal instance and set the associated Document
      ThesisFinal thesisFinal = new ThesisFinal();
      String finalFile = "final_document.pdf";
      Document finalDocument = new Document(finalFile);
      documentRepository.save(finalDocument);
      thesisFinal.setDocument(finalDocument);
      thesisFinalRepository.save(thesisFinal);

      Student student2 = new Student("name3", "password2", "alice", 16.7, master1);
      Student student3 = new Student("name4", "password2", "tiago", 16.7, master1);
      ThesisTheme thesisTheme3 = new ThesisTheme("projeto", "descricao", 300.02, teacher);
      ThesisFinal thesisFinal2 = new ThesisFinal();
      ThesisFinal thesisFinal3 = new ThesisFinal();
      Document finalDocument2 = new Document(finalFile);
      Document finalDocument3 = new Document(finalFile);
      studentRepository.save(student2);
      studentRepository.save(student3);
      documentRepository.save(finalDocument2);
      documentRepository.save(finalDocument3);
      thesisFinal2.setDocument(finalDocument2);
      thesisFinal3.setDocument(finalDocument3);
      thesisThemeRepository.save(thesisTheme3);
      thesisFinalRepository.save(thesisFinal2);
      thesisFinalRepository.save(thesisFinal3);

      CompanyEmployee companyEmployee = new CompanyEmployee("Jachy", "pass", "Jacky");
      Master master = new Master("LTI", 1232L);
      Dissertation dissertation =
          new Dissertation("The ow", student, thesisTheme, thesisFinal, "ow");
      Project project = new Project("The ow2", student2, thesisTheme2, thesisFinal2, "ow");

      Jury jury = new Jury(thesisDefence);
      Teacher teacher2 = new Teacher("Jake", "admin", "a", true);
      teacherRepository.save(teacher2);

      dissertationRepository.save(dissertation);
      projectRepository.save(project);

      companyEmployee.addProject(project);
      companyEmployee.addThesisTheme(thesisTheme);
      master.addStudent(student);

      jury.addTeacher(teacher);
      companyEmployeeRepository.save(companyEmployee);
      masterRepository.save(master);

      juryRepository.save(jury);
      teacherRepository.save(teacher2);

      thesisTheme.addMaster(master);
      thesisThemeRepository.save(thesisTheme);
    };
  }
}
