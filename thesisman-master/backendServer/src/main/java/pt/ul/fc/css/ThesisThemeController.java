package pt.ul.fc.css;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.services.ThesisThemeDTO;
import pt.ul.fc.css.services.ThesisThemesHandler;

@RestController
@RequestMapping("/api")
public class ThesisThemeController {

  private final ThesisThemesHandler thesisThemesHandler;

  @Autowired
  public ThesisThemeController(ThesisThemesHandler thesisThemesHandler) {
    this.thesisThemesHandler = thesisThemesHandler;
  }
  // --------------------------------------------------verificar estas 2 acho que tenho de emter uma
  // cena de nao ser ok
  @GetMapping("/ThesisThemes/{id}")
  public ResponseEntity<List<ThesisThemeDTO>> getThesisThemes(@PathVariable Long id) {
    List<ThesisThemeDTO> compatibleThesisThemes = thesisThemesHandler.getCompatibleThesisThemes(id);
    if (compatibleThesisThemes == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.ok(compatibleThesisThemes);
  }

  @GetMapping("/ThesisThemesUser/{id}")
  public ResponseEntity<List<ThesisThemeDTO>> getThesisThemesCurrentUser(@PathVariable Long id) {
    List<ThesisThemeDTO> userThesisThemes = thesisThemesHandler.getUserThesisThemes(id);
    if (userThesisThemes == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.ok(userThesisThemes);
  }

  @PostMapping("/ThesisThemes/submit")
  public ResponseEntity<String> submitThesisTheme(@RequestBody Map<String, String> requestBody) {
    String userIdString = requestBody.get("userId");
    String thesisThemeIdString = requestBody.get("thesisThemeId");

    if (userIdString == null || thesisThemeIdString == null) {
      return ResponseEntity.badRequest().body("userId and thesisThemeId are required.");
    }

    Long userId;
    Long thesisThemeId;
    try {
      userId = Long.parseLong(userIdString);
      thesisThemeId = Long.parseLong(thesisThemeIdString);
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().body("Invalid userId or thesisThemeId format.");
    }
    boolean wasSubmitted = thesisThemesHandler.checkThesisThemeSubmission(userId, thesisThemeId);
    // Here, you can handle the submission logic based on the userId and thesisThemeId
    if (!wasSubmitted) return ResponseEntity.ok("Thesis theme wansnt submeted!");
    return ResponseEntity.ok("Thesis theme submitted successfully!");
  }

  @DeleteMapping("/ThesisThemes/delete")
  public ResponseEntity<String> deleteThesisThemeFromUser(
      @RequestParam("userId") Long userId, @RequestParam("thesisThemeId") Long thesisThemeId) {
    if (userId == null || thesisThemeId == null) {
      return ResponseEntity.badRequest().body("userId and thesisThemeId are required.");
    }

    boolean wasDeleted = thesisThemesHandler.deleteThesisThemeFromUser(userId, thesisThemeId);

    if (!wasDeleted) {
      return ResponseEntity.ok("Thesis theme wasn't deleted!");
    } else {
      return ResponseEntity.ok("Thesis theme deleted successfully!");
    }
  }
}
