package Dao;

import Models.User;
import java.util.List;

public interface UserDAO {

    // UserDAO(Data Access Object) this interface, and it's implementation are created to
    // separate data persistence logic(database operations) from the business logic(services, controllers)

    void save(User user);
    User findById(Long id);
    List<User> findAll();
    void delete(Long id);
}
