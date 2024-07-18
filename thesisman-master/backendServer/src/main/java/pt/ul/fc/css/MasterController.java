package pt.ul.fc.css;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.entities.Master;
import pt.ul.fc.css.repositories.MasterRepository;

@RestController
@RequestMapping("/api")
public class MasterController {

  private final MasterRepository masterRepository;

  public MasterController(MasterRepository masterRepository) {
    this.masterRepository = masterRepository;
  }

  @GetMapping("/master/{id}")
  public Master getMasterById(@PathVariable Long id) {
    return masterRepository.findById(id).orElse(null);
  }

  @GetMapping("/masters")
  public List<Master> getAllMasters() {
    return masterRepository.findAll();
  }

  @PostMapping("/master")
  public Master postMaster(@RequestBody Master master) {
    return masterRepository.save(master);
  }

  @PutMapping("/master/{id}")
  public Master updateMaster(@PathVariable Long id, @RequestBody Master master) {
    if (masterRepository.existsById(id)) {
      return masterRepository.save(master);
    } else {
      return null; // Handle case where a master with the given ID doesn't exist
    }
  }

  @DeleteMapping("/master/{id}")
  public void deleteMaster(@PathVariable Long id) {
    masterRepository.deleteById(id);
  }
}
