import java.sql.Connection;
import java.sql.DriverManager;

public class PostreTest {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/safesign";
        String user = "taskuser";
        String password = "asdaer";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("Connected to PostgreSQL successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
