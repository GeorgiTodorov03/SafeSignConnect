package Models;

public class User {
    private Long id;
    private String userName;
    private String email;
    private String passwordHash;

    public User(Long id, String userName, String email, String passwordHash) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public User(String userName, String email, String passwordHash) {
        this(null, userName, email, passwordHash);
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}