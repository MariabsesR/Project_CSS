package pt.ul.fc.css;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.entities.Thesis;
import pt.ul.fc.css.repositories.ThesisRepository;

@RestController
@RequestMapping("/api")
public class ThesisController {

  private final ThesisRepository thesisRepository;

  public ThesisController(ThesisRepository thesisRepository) {
    this.thesisRepository = thesisRepository;
  }

  @GetMapping("/thesis/{id}")
  public Thesis getThesisById(@PathVariable Long id) {
    return thesisRepository.findById(id).orElse(null);
  }

  @GetMapping("/theses")
  public List<Thesis> getAllThesis() {
    return thesisRepository.findAll();
  }

  @PostMapping("/thesis")
  public Thesis postThesis(@RequestBody Thesis thesis) {
    return thesisRepository.save(thesis);
  }

  @PutMapping("/thesis/{id}")
  public Thesis updateThesis(@PathVariable Long id, @RequestBody Thesis thesis) {
    if (thesisRepository.existsById(id)) {
      return thesisRepository.save(thesis);
    } else {
      return null; // Handle case where a thesis with the given ID doesn't exist
    }
  }

  @DeleteMapping("/thesis/{id}")
  public void deleteThesis(@PathVariable Long id) {
    thesisRepository.deleteById(id);
  }
}
