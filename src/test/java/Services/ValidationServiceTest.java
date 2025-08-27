package Services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationServiceTest {

    private final ValidationService validationService = new ValidationService();

    @Test
    void testValidEmail() {
        assertTrue(validationService.isValidEmail("test@example.com"));
        assertFalse(validationService.isValidEmail("invalid-email"));
    }

    @Test
    void testValidPassword() {
        assertTrue(validationService.isValidPassword("Strong1@"));
        assertFalse(validationService.isValidPassword("weak"));
    }
}
