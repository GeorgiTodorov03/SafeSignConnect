package Services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CaptchaServiceTest {

    @Test
    void testCaptcha() {
        CaptchaService captcha = new CaptchaService();
        String question = captcha.generateCaptcha();
        assertTrue(question.contains("+"));
        assertTrue(captcha.validateCaptcha(
                Integer.parseInt(question.split("\\+")[1].replace("?", "").trim()) +
                        Integer.parseInt(question.split(" ")[2])
        ));
    }
}
