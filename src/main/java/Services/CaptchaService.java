package Services;

import java.util.Random;

public class CaptchaService {

    private int a;
    private int b;

    public String generateCaptcha() {
        Random rand = new Random();
        a = rand.nextInt(10);
        b = rand.nextInt(10);
        return "What is " + a + " + " + b + "?";
    }

    public boolean validateCaptcha(int answer) {
        return (a + b) == answer;
    }
}
