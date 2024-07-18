package pt.ul.fc.di.css.javafxexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import pt.ul.fc.di.css.javafxexample.model.DataModel;

public class AuthService {
  private DataModel dataModel;

  public String authenticate(String credentialsJson) throws IOException {
    String authenticationEndpoint = dataModel.getUrlServer() + "/authenticate";
    String charset = "UTF-8";
    String CRLF = "\r\n";

    URL url = new URL(authenticationEndpoint);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setDoOutput(true);
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/json; charset=" + charset);

    try (OutputStream output = connection.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true)) {
      writer.print(credentialsJson);
      writer.flush();
    }

    int responseCode = connection.getResponseCode();
    System.out.println("Response code is " + responseCode);

    if (responseCode == HttpURLConnection.HTTP_OK) {
      try (BufferedReader in =
          new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
          response.append(line);
        }
        System.out.println("Authentication successful: " + response.toString());
        return response.toString();
      }
    } else {
      try (BufferedReader in =
          new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
          response.append(line);
        }
        System.out.println("Error: " + response.toString());
        return "Error: " + response.toString();
      }
    }
  }

  public void setDataModel(DataModel dataModel) {
    this.dataModel = dataModel;
  }
}
