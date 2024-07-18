package pt.ul.fc.css.services;

public class MasterDTO {
    private Long id;
    private String name;
    private Long code;

    // Default constructor
    public MasterDTO() {
    }

    // Parameterized constructor
    public MasterDTO(Long id, String name, Long code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    // Getters and Setters
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

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "MasterDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
