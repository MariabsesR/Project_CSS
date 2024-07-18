package pt.ul.fc.css.services;

import java.util.List;

public class CompanyEmployeeDTO {
    private Long id;
    private String username;
    private String password; // Add this line
    private String name;
    private List<Long> projectIds;
    private List<Long> thesisThemeIds;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { // Add this method
        return password;
    }

    public void setPassword(String password) { // Add this method
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }

    public List<Long> getThesisThemeIds() {
        return thesisThemeIds;
    }

    public void setThesisThemeIds(List<Long> thesisThemeIds) {
        this.thesisThemeIds = thesisThemeIds;
    }
}
