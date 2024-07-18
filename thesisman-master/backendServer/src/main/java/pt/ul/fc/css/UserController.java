package pt.ul.fc.css;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.services.AuthenticationHandler;

@RestController
@RequestMapping("/api")
public class UserController {

  private final AuthenticationHandler authenticationHandler;

  @Autowired
  public UserController(AuthenticationHandler authenticationHandler) {
    this.authenticationHandler = authenticationHandler;
  }

  @PostMapping("/authenticate")
  public ResponseEntity<String> authenticateUser(@RequestBody String credentialsJson) {
    System.out.println("TENTOU DAR POSTTTTT\n\n\n\n\n");
    Long id = authenticationHandler.authenticateStudent(credentialsJson);
    if (id != null) {
      return ResponseEntity.ok(id.toString());
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }
  }
}
