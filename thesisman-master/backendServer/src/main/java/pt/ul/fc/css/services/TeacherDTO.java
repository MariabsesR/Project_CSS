package pt.ul.fc.css.services;

import java.util.List;

public class TeacherDTO {
    private Long id;
    private String name;
    private String username;
    private String password;
    private List<Long> thesisIds;
    private boolean isAdmin;

    public TeacherDTO() {
    }

    public TeacherDTO(Long id, String name, String username, String password, List<Long> thesisIds, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.thesisIds = thesisIds;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getThesisIds() {
        return thesisIds;
    }

    public void setThesisIds(List<Long> thesisIds) {
        this.thesisIds = thesisIds;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "TeacherDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", thesisIds=" + thesisIds +
                ", isAdmin=" + isAdmin + // Add this field to toString
                '}';
    }
}
