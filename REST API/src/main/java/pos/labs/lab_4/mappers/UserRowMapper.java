package pos.labs.lab_4.mappers;

import org.springframework.jdbc.core.RowMapper;
import pos.labs.lab_4.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int numberRow) throws SQLException {
        return new User(resultSet.getInt("uid"),
                resultSet.getString("user_name"),
                resultSet.getString("parola"),
                resultSet.getInt("role_id")
        );
    }
}
