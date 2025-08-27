package Services;

import Dao.Impl.UserDAOImpl;
import Dao.UserDAO;
import Models.User;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    private final UserDAO userDAO;
    private final ValidationService validationService;

    public AuthService() {
        this.userDAO = new UserDAOImpl();
        this.validationService = new ValidationService();
    }

    public AuthService(UserDAO userDAO, ValidationService validationService) {
        this.userDAO = userDAO;
        this.validationService = validationService;
    }

    public boolean register(String username, String email, String password) {
        if (!validationService.isValidEmail(email) ||
                !validationService.isValidPassword(password)) {
            return false;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, email, hashedPassword);
        return userDAO.createUser(user);
    }

    public User login(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    public boolean changePassword(String email, String newPassword) {
        if (!validationService.isValidPassword(newPassword)) {
            return false;
        }

        User user = userDAO.getUserByEmail(email);
        if (user == null) return false;

        String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        user.setPasswordHash(newHash);
        return userDAO.updateUser(user);
    }

    public boolean update(int userId, String newUsername, String newEmail, String newPassword) {
        User user = userDAO.getUserById(userId);
        if (user == null) return false;

        // username (optional)
        if (newUsername != null && !newUsername.isBlank()) {
            user.setUsername(newUsername.trim());
        }

        // email (optional)
        if (newEmail != null && !newEmail.isBlank()) {
            if (!validationService.isValidEmail(newEmail)) return false;
            user.setEmail(newEmail.trim());
        }

        // password (optional)
        if (newPassword != null && !newPassword.isBlank()) {
            if (!validationService.isValidPassword(newPassword)) return false;
            String hash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            user.setPasswordHash(hash);
        }

        try {
            userDAO.updateUser(user);   // DAO is void; assume success if no exception
            return true;
        } catch (Exception e) {
            // log if you have a logger
            return false;
        }
    }
}
