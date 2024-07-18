package pt.ul.fc.css.services;

public class SessionDTO {
    private Long userId;

    // Constructor, getters, setters
    public SessionDTO(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
