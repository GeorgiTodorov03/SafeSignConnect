package Dao;

import Models.User;
import java.util.List;

public interface UserDAO {

    // UserDAO(Data Access Object) this interface, and it's implementation are created to
    // separate data persistence logic(database operations) from the business logic(services, controllers)

    boolean createUser(User user);                    // Register
    User getUserById(int id);                   // Get by ID
    User getUserByUsername(String username);    // Find by username
    User getUserByEmail(String email);          // Find by email
    boolean updateUser(User user);                 // Change username/email/password
    void deleteUser(int id);                    // Delete account
    void updateLastLogin(int id);               // Track last login
    void incrementFailedAttempts(int id);       // Security lock
    void resetFailedAttempts(int id);
    void lockUser(int id);
}
