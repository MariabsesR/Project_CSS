package pt.ul.fc.di.css.javafxexample.control;

import java.util.HashMap;
import javafx.scene.Scene;
import javafx.stage.Stage;

// retirado de https://stackoverflow.com/questions/37200845/how-to-switch-scenes-in-javafx

public class ScreenController {
  private HashMap<String, Scene> screenMap = new HashMap<>();
  private Stage main;

  public ScreenController(Stage main) {
    this.main = main;
  }

  public void addScene(String name, Scene scene) {
    screenMap.put(name, scene);
  }

  public void removeScene(String scene) {
    screenMap.remove(scene);
  }

  public void activate(String name) {
    Scene scene = screenMap.get(name);

    if (scene != null) {
      main.setScene(scene);
      main.show();
    } else {
      System.out.println("Scene " + name + " not found!");
    }
  }
}
