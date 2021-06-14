package pos.labs.lab_4.interfaces;

import pos.labs.lab_4.models.User;
import pos.labs.lab_4.models.UserLogin;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    User getUserById(Integer id);

    Integer addUser(User user) throws java.sql.SQLIntegrityConstraintViolationException;

    void deleteUser(Integer id);

    Integer addUserId(User user);

    void updateUser(User user);

    User authenticate(UserLogin userLogin);
}
