import Dao.Impl.UserDAOImpl;
import Dao.UserDAO;
import Models.User;
import Utils.DatabaseConnection;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {

        try(Connection connection = DatabaseConnection.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(connection);

            //Add new user
            User user = new User("Georgi", "Georgi@example.com", "asdaer");
            userDAO.save(user);

            //Fetch all users
            userDAO.findAll().forEach(u ->
                    System.out.println(u.getId() + " " + u.getUserName())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
