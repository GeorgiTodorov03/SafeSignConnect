package Dao;

import Models.User;
import java.util.List;

public interface UserDAO {

    void save(User user);
    User findById(Long id);
    List<User> findAll();
    void delete(Long id);
}
