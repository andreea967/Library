package pos.labs.lab_4.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pos.labs.lab_4.interfaces.IUserService;
import pos.labs.lab_4.mappers.UserRowMapper;
import pos.labs.lab_4.models.User;
import pos.labs.lab_4.models.UserLogin;

import java.util.List;

@Service
public class UserService implements IUserService {
    private JdbcTemplate jdbcTemplate;
    private UserRowMapper userRowMapper;

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = new UserRowMapper();
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        List<User> userList = jdbcTemplate.query(query, userRowMapper);

        return userList;
    }

    @Override
    public User getUserById(Integer id) {
        String query = "SELECT * FROM users WHERE uid=?"; 
        List<User> userList = jdbcTemplate.query(query, userRowMapper,id);
        if(userList.size() == 0){
            return null;
        }else{
            return userList.get(0);
        }
    }

    @Override
    public Integer addUser(User user)throws java.sql.SQLIntegrityConstraintViolationException{
        String query = "INSERT INTO users (user_name, parola, role_id) VALUES (?,?,?)";
        String queryId = "SELECT * from users WHERE user_name = ? AND parola = ? AND role_id = ?";
        jdbcTemplate.update(query, user.username, user.password, user.roleId);
        List<User> userList = jdbcTemplate.query(queryId, userRowMapper, user.username, user.password, user.roleId);
        if(userList.size() == 0){
            return null;
        }else{
            return userList.get(0).uid;
        }

    }

    @Override
    public void deleteUser(Integer id) {
        String query = "DELETE FROM users WHERE uid=?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public Integer addUserId(User user) {
        String query = "INSERT INTO users (uid, user_name, parola, role_id) VALUES (?,?,?,?)";

        jdbcTemplate.update(query,user.uid, user.username, user.password, user.roleId);
        return user.uid;
    }

    @Override
    public void updateUser(User user) {
        String query = "UPDATE users SET user_name = ?, parola = ?, role_id = ? WHERE uid = ?";
        jdbcTemplate.update(query, user.username, user.password, user.roleId, user.uid);


    }

    @Override
    public User authenticate(UserLogin userLogin) {
        String query = "SELECT * FROM users WHERE user_name=? AND parola=?";
        List<User> userList = jdbcTemplate.query(query, userRowMapper, userLogin.username, userLogin.password);
        if(userList.size() == 0){
            return null;
        }else{
            return userList.get(0);
        }
    }

}
