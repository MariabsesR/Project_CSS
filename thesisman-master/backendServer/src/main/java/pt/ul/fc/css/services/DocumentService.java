package pt.ul.fc.css.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pt.ul.fc.css.entities.Document;
import pt.ul.fc.css.entities.Student;
import pt.ul.fc.css.entities.Thesis;
import pt.ul.fc.css.entities.ThesisFinal;
import pt.ul.fc.css.entities.ThesisProposal;
import pt.ul.fc.css.repositories.DocumentRepository;
import pt.ul.fc.css.repositories.StudentRepository;
import pt.ul.fc.css.repositories.ThesisFinalRepository;
import pt.ul.fc.css.repositories.ThesisProposalRepository;
import pt.ul.fc.css.repositories.ThesisRepository;

@Service
public class DocumentService {
  private final Path rootLocation;
  private final DocumentRepository documentRepository;
  private final StudentRepository studentRepository;
  private final ThesisProposalRepository thesisProposalRepository;
  private final ThesisFinalRepository thesisFinalRepository;
  private final ThesisRepository thesisRepository;

  @Autowired
  public DocumentService(
      StorageProperties properties,
      DocumentRepository documentRepository,
      StudentRepository studentRepository,
      ThesisProposalRepository thesisProposalRepository,
      ThesisRepository thesisRepository,
      ThesisFinalRepository thesisFinalRepository) {
    if (properties.getLocation().trim().isEmpty()) {
      throw new StorageException("File upload location cannot be empty.");
    }
    this.rootLocation = Paths.get(properties.getLocation());
    this.documentRepository = documentRepository;
    this.studentRepository = studentRepository;
    this.thesisProposalRepository = thesisProposalRepository;
    this.thesisFinalRepository = thesisFinalRepository;
    this.thesisRepository = thesisRepository;
  }

  @Transactional
  public String storeProposal(MultipartFile file, String userId) {
    // Check if the student exists
    Optional<Student> studentOptional = studentRepository.findById(Long.parseLong(userId));
    if (studentOptional.isEmpty()) {
      return "Student couldn't be found";
    }

    Student student = studentOptional.get();
    Optional<Thesis> thesisOptional = thesisRepository.findByStudentId(Long.parseLong(userId));
    if (thesisOptional.isEmpty()) {
      return "The student has no thesis, hence can't submit files";
    }
    Thesis thesis = thesisOptional.get();
    if (thesis.getThesisProposal().size() >= 2) {
      return "You have used all possible tries";
    }

    if (thesis.getThesisProposal().size() == 1) {
      if (thesis.getThesisProposal().get(0).getThesisDefence() == null) {
        return "You must defend your original submission before attemping another";
      }
      if (thesis.getThesisProposal().get(0).getThesisDefence().getGrade() >= 10) {
        return "Your last thesis defence has a grade >= 10, hence you can't try again";
      }
    }

    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file.");
      }

      Path destinationFile =
          this.rootLocation
              .resolve(Paths.get(file.getOriginalFilename()))
              .normalize()
              .toAbsolutePath();

      if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
        // This is a security check
        throw new StorageException("Cannot store file outside current directory.");
      }

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }

      // Create a Document entity and save the file path
      Document document = new Document(destinationFile.toString());
      documentRepository.save(document);

      ThesisProposal thesisProposal = new ThesisProposal();
      thesisProposal.setLastDocumentSubmited(document);
      thesisProposalRepository.save(thesisProposal);

      thesis.addThesisProposal(thesisProposal);
      thesisRepository.save(thesis);

      return "Document was saved successfully";
    } catch (IOException e) {
      throw new StorageException("Failed to store file.", e);
    }
  }

  @Transactional
  public String storeFinal(MultipartFile file, String userId) {
    // Check if the student exists
    Optional<Student> studentOptional = studentRepository.findById(Long.parseLong(userId));
    if (studentOptional.isEmpty()) {
      return "Student couldn't be found";
    }

    Student student = studentOptional.get();
    Optional<Thesis> thesisOptional = thesisRepository.findByStudentId(Long.parseLong(userId));
    if (thesisOptional.isEmpty()) {
      return "The student has no thesis, hence can't submit files";
    }
    Thesis thesis = thesisOptional.get();
    if (thesis.getThesisProposal().size() >= 2) {
      return "You have used all possible tries";
    }

    if (thesis.getThesisFinal() != null) {
      return "you already Submitted a file for your thesis Final";
    }

    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file.");
      }

      Path destinationFile =
          this.rootLocation
              .resolve(Paths.get(file.getOriginalFilename()))
              .normalize()
              .toAbsolutePath();

      if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
        // This is a security check
        throw new StorageException("Cannot store file outside current directory.");
      }

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }

      // Create a Document entity and save the file path
      Document document = new Document(destinationFile.toString());
      documentRepository.save(document);

      ThesisFinal thesisFinal = new ThesisFinal();
      thesisFinal.setDocument(document);
      thesisFinalRepository.save(thesisFinal);

      thesis.setThesisFinal(thesisFinal);
      ;
      thesisRepository.save(thesis);

      return "Document was saved successfully";
    } catch (IOException e) {
      throw new StorageException("Failed to store file.", e);
    }
  }

  public void init() {
    try {
      System.out.println("Initializing storage");
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }
  
  public List<Document> getAllDocuments() {
      return documentRepository.findAll();
  }

  public Document getRoomById(Long id) {
      return documentRepository.findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Document not found"));
  }
}
