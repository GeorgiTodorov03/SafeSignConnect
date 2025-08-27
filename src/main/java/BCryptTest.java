import org.mindrot.jbcrypt.BCrypt;

public class BCryptTest {
    public static void main(String[] args) {
        String password = "mySecret123";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        System.out.println("Password: " + password);
        System.out.println("Hash: " + hashed);

        boolean matches = BCrypt.checkpw("mySecret123", hashed);
        System.out.println("✅ Password verification: " + matches);
    }
}
