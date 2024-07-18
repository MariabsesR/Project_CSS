package pt.ul.fc.css;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ul.fc.css.services.DocumentService;

@RestController
@RequestMapping("/api")
public class DocumentController {

  private final DocumentService documentService;

  @Autowired
  public DocumentController(DocumentService documentService) {
    this.documentService = documentService;
  }

  @PostMapping("/files/ThesisProposal")
  public ResponseEntity<String> thesisProposalUpload(
      @RequestParam("file") MultipartFile file,
      @RequestParam("userId") String userId,
      RedirectAttributes redirectAttributes) {

    String result = documentService.storeProposal(file, userId);
    String fileName = file.getOriginalFilename();
    redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + fileName + "!");
    System.out.println("The answer from the service was " + result + "\n\n\n");
    System.out.println("Received file: " + fileName + " from user: " + userId + "\n\n\n\n");

    if (result.equals("Document was saved successfully")) return ResponseEntity.ok(result);

    return ResponseEntity.badRequest().body(result);
  }

  @PostMapping("/files/ThesisFinal")
  public ResponseEntity<String> thesisFinalUpload(
      @RequestParam("file") MultipartFile file,
      @RequestParam("userId") String userId,
      RedirectAttributes redirectAttributes) {

    String result = documentService.storeFinal(file, userId);
    String fileName = file.getOriginalFilename();
    redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + fileName + "!");
    System.out.println("The answer from the service was " + result + "\n\n\n");
    System.out.println("Received file: " + fileName + " from user: " + userId + "\n\n\n\n");

    if (result.equals("Document was saved successfully")) return ResponseEntity.ok(result);

    return ResponseEntity.badRequest().body(result);
  }
}
