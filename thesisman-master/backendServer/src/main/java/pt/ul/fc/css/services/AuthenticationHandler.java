package pt.ul.fc.css.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.entities.Student;
import pt.ul.fc.css.entities.User;
import pt.ul.fc.css.repositories.StudentRepository;
import pt.ul.fc.css.repositories.UserRepository;

@Service
public class AuthenticationHandler {
  private final UserRepository userRepository;
  private final StudentRepository studentRepository;

  @Autowired
  public AuthenticationHandler(UserRepository userRepository, StudentRepository studentRepository) {
    this.userRepository = userRepository;
    this.studentRepository = studentRepository;
  }

  public Long authenticateStudent(String credentialsJson) {
    System.out.println(credentialsJson + " received \n\n\n\n");
    System.out.println("tosta mitstaaaaaaaaaaaaaaaaaaaaaa\n\n\n\n\n");

    try {
      JSONObject jsonObject = new JSONObject(credentialsJson);
      String username = jsonObject.getString("username");
      String password = jsonObject.getString("password");

      System.out.println("Received username " + username + ", password " + password);
      // Since we are doing mock of the fucntionality any password will be accepted but the user
      // must still be a student
      User user = userRepository.findByUsername(username);
      if (user != null) {

        // che kfi user is a student since javafx is meant for the student
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
          System.out.println("its a user but it aint a student\n\n\n\n\n");
          return null;
        }
        return user.getId();
      } else {
        System.out.println("its not a user\n\n\n\n\n");
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
