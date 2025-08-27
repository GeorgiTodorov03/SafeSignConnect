package Models;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private int failedAttempts;
    private boolean isLocked;

    // Constructors
    public User(Long id, String username, String email, String passwordHash,
                LocalDateTime createdAt, LocalDateTime lastLogin,
                int failedAttempts, boolean isLocked) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.failedAttempts = failedAttempts;
        this.isLocked = isLocked;
    }

    public User(String username, String email, String passwordHash) {
        this(Long.valueOf(0), username, email, passwordHash, null, null, 0, false);
    }

    // Getters only (no direct setters for security-critical fields)
    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    public int getFailedAttempts() {
        return failedAttempts;
    }
    public boolean isLocked() {
        return isLocked;
    }
}