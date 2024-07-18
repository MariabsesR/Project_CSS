Grupo:
António Estêvão, Nº 58203
Maria Rocha, Nº 58208
Jacky Xu, Nº 58218

To run the project:
To run the backend Server:
-inside the folder backendServer run the file run.sh while docker is open
-to access company login http://localhost:8080/api/logincompany
-to access teacher login http://localhost:8080/api/loginteacher
To run the javaFX App :
-change localhost and 8080 to the port the backend server is running on. In case its in the same computer then use the generic localhost and 8080
-must be run inside of folder javafxexample
java --module-path "javafx-sdk-21.0.3\lib" --add-modules javafx.controls,javafx.fxml,javafx.web -jar javaFXApp_students.jar http://localhost:8080/api
