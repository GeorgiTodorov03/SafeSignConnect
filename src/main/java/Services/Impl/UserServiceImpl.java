package Services.Impl;

import Dao.Impl.UserDAOImpl;
import Dao.UserDAO;
import Models.User;
import Services.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public boolean registerUser(String username, String password, String email) {

        //Check if user exists
        if(userDAO.getUserByUsername(username) != null) {
            System.out.println("User already exists.");
            return false;
        }

        //Hash password before saving
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = new User(username, email, hashedPassword);

        return userDAO.createUser(newUser);
    }

    @Override
    public User loginUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);

        if(user == null) {
            System.out.println("User not found.");
            return null;
        }

        //Check password hash
        if(BCrypt.checkpw(password, user.getPasswordHash())) {
            user.setLastLogin(LocalDateTime.now());
            userDAO.updateUser(user);
            return user;
        }

        System.out.println("Incorrect password.");
        return null;

    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = userDAO.getUserByUsername(username);

        if(user == null)
            return false;

        // Verify old password
        if(!BCrypt.checkpw(oldPassword, user.getPasswordHash()))
            return false;

        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        user.setPasswordHash(hashedNewPassword);

        try {
            userDAO.updateUser(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean changeUsername(String oldUsername, String newUsername, String password) {
        User user = userDAO.getUserByUsername(oldUsername);
        if (user == null)
            return false;

        if (!BCrypt.checkpw(password, user.getPasswordHash())) {
            return false;
        }

        user.setUsername(newUsername);

        try {
            userDAO.updateUser(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateUser(int id, String newUsername, String newPassword) {
        User user = userDAO.getUserById(id);
        if (user == null) return false;

        if (newUsername != null && !newUsername.isBlank()) {
            user.setUsername(newUsername);
        }
        if (newPassword != null && !newPassword.isBlank()) {
            String hashed = hashPassword(newPassword);
            user.setPasswordHash(hashed);
        }

        userDAO.updateUser(user);
        return true;
    }

    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }
}
