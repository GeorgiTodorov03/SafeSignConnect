package Services;

import Dao.UserDAO;
import Models.User;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {
    private final UserDAO userDAO = mock(UserDAO.class);
    private final AuthService authService = new AuthService(userDAO, new ValidationService());

    @Test
    void testRegisterInvalidEmail() {
        assertFalse(authService.register("user", "bad-email", "Strong1@"));
    }

    @Test
    void testLoginSuccess() {
        User user = new User("user", "test@example.com",
                org.mindrot.jbcrypt.BCrypt.hashpw("Strong1@", org.mindrot.jbcrypt.BCrypt.gensalt()));
        when(userDAO.getUserByEmail("test@example.com")).thenReturn(user);

        assertNotNull(authService.login("test@example.com", "Strong1@"));
    }

    @Test
    void testLoginFailure() {
        when(userDAO.getUserByEmail("notfound@example.com")).thenReturn(null);
        assertNull(authService.login("notfound@example.com", "password"));
    }
}
