package Services;

import Models.User;

public interface UserService {

    boolean registerUser(String username, String password, String email);
    User loginUser(String username, String password);
    boolean changePassword(String username, String oldPassword, String newPassword);
    boolean changeUsername(String oldUsername, String newUsername, String password);
    boolean updateUser(int id, String newUsername, String newPassword);
}
