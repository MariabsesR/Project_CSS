package pt.ul.fc.di.css.javafxexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import pt.ul.fc.di.css.javafxexample.model.DataModel;
import pt.ul.fc.di.css.javafxexample.model.ThesisTheme;

public class ThesisThemesService {

  private DataModel dataModel;

  public void setDataModel(DataModel dataModel) {
    this.dataModel = dataModel;
  }

  public List<ThesisTheme> fetchThesisThemes(Long id) throws IOException {
    String uri = dataModel.getUrlServer() + "/ThesisThemes/" + id;
    HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
    connection.setRequestMethod("GET");
    connection.setRequestProperty("Content-Type", "application/json");

    int responseCode = connection.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      try (BufferedReader in =
          new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
          response.append(line);
        }
        return parseThesisThemes(response.toString());
      }
    } else {
      throw new IOException("Failed to fetch thesis themes: " + responseCode);
    }
  }

  public List<ThesisTheme> fetchThesisThemesCurrentUser(Long id) throws IOException {
    String uri = dataModel.getUrlServer() + "/ThesisThemesUser/" + id;
    HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
    connection.setRequestMethod("GET");
    connection.setRequestProperty("Content-Type", "application/json");

    int responseCode = connection.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      try (BufferedReader in =
          new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
          response.append(line);
        }
        return parseThesisThemes(response.toString());
      }
    } else {
      throw new IOException("Failed to fetch thesis themes: " + responseCode);
    }
  }

  private List<ThesisTheme> parseThesisThemes(String responseBody) {
    List<ThesisTheme> themes = new ArrayList<>();
    try {
      Object json = new JSONTokener(responseBody).nextValue();
      if (json instanceof JSONArray) {
        JSONArray jsonArray = (JSONArray) json;
        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject jsonObject = jsonArray.getJSONObject(i);
          ThesisTheme theme = parseThesisTheme(jsonObject);
          themes.add(theme);
        }
      } else if (json instanceof JSONObject) {
        JSONObject jsonObject = (JSONObject) json;
        ThesisTheme theme = parseThesisTheme(jsonObject);
        themes.add(theme);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return themes;
  }

  private ThesisTheme parseThesisTheme(JSONObject jsonObject) {
    ThesisTheme theme = new ThesisTheme();
    theme.setId(jsonObject.getLong("id"));
    theme.setTitle(jsonObject.getString("title"));
    theme.setDescription(jsonObject.getString("description"));
    theme.setMonthlyPay(jsonObject.getDouble("monthlyPay"));
    return theme;
  }

  public void submitThesisTheme(ThesisTheme theme, Long userId) throws IOException {
    String uri = dataModel.getUrlServer() + "/ThesisThemes/submit";
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("userId", userId);
    jsonObject.put("thesisThemeId", theme.getId());

    HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setDoOutput(true);

    try (OutputStream output = connection.getOutputStream()) {
      output.write(jsonObject.toString().getBytes());
      output.flush();
    }

    int responseCode = connection.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      try (BufferedReader in =
          new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
          response.append(line);
        }
        System.out.println("Response: " + response.toString());
      }
    } else {
      throw new IOException("Failed to submit thesis theme: " + responseCode);
    }
  }

  public void deleteThesisTheme(Long userId, ThesisTheme theme) throws IOException {
    String uri = dataModel.getUrlServer() + "/ThesisThemes/delete";
    String userIdParam = "userId=" + userId;
    String thesisThemeIdParam = "thesisThemeId=" + theme.getId();
    uri += "?" + userIdParam + "&" + thesisThemeIdParam;

    HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
    connection.setRequestMethod("DELETE");
    connection.setRequestProperty("Content-Type", "application/json");

    int responseCode = connection.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      try (BufferedReader in =
          new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
          response.append(line);
        }
        System.out.println("Response: " + response.toString());
      }
    } else {
      throw new IOException("Failed to delete thesis theme: " + responseCode);
    }
  }
}
