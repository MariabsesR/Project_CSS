package pt.ul.fc.di.css.javafxexample;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import pt.ul.fc.di.css.javafxexample.model.DataModel;

public class FileSubmissionService {

  private DataModel dataModel;

  public void setDataModel(DataModel dataModel) {
    this.dataModel = dataModel;
  }

  public String uploadFile(File file, String typeOfFile, Long userId) throws IOException {
    String uploadUrl = dataModel.getUrlServer() + "/files/" + typeOfFile;
    String charset = "UTF-8";
    String boundary = Long.toHexString(System.currentTimeMillis());
    String CRLF = "\r\n";

    URLConnection connection = new URL(uploadUrl).openConnection();
    connection.setDoOutput(true);
    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

    try (OutputStream output = connection.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true)) {

      // Send userId as a form field
      writer.append("--" + boundary).append(CRLF);
      writer.append("Content-Disposition: form-data; name=\"userId\"").append(CRLF);
      writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
      writer.append(CRLF).append(userId.toString()).append(CRLF).flush();

      // Send file as a form field
      writer.append("--" + boundary).append(CRLF);
      writer
          .append(
              "Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"")
          .append(CRLF);
      writer
          .append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName()))
          .append(CRLF);
      writer.append("Content-Transfer-Encoding: binary").append(CRLF);
      writer.append(CRLF).flush();
      Files.copy(file.toPath(), output);
      output.flush();
      writer.append(CRLF).flush();

      // End of multipart/form-data.
      writer.append("--" + boundary + "--").append(CRLF).flush();
    }

    int responseCode = ((HttpURLConnection) connection).getResponseCode();
    System.out.println("Response code is " + responseCode);

    if (responseCode == HttpURLConnection.HTTP_OK) {

      System.out.println("File saved");
      return "File saved ";
    } else {

      try (BufferedReader in =
          new BufferedReader(
              new InputStreamReader(((HttpURLConnection) connection).getErrorStream()))) {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
          response.append(line);
        }
        System.out.println("Error : " + response.toString());
        return "Error : " + response.toString();
      }
    }
  }
}
