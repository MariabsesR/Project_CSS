module pt.ul.fc.di.css.javafxexample {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;
  requires java.net.http;
  requires org.json;

  opens pt.ul.fc.di.css.javafxexample to
      javafx.fxml,
      javafx.web;
  opens pt.ul.fc.di.css.javafxexample.control to
      javafx.fxml;

  exports pt.ul.fc.di.css.javafxexample;
}
