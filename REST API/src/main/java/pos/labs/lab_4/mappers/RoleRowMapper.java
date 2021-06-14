package pos.labs.lab_4.mappers;

import org.springframework.jdbc.core.RowMapper;
import pos.labs.lab_4.models.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role>{

    @Override
    public Role mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Role(
                resultSet.getInt("role_id"),
                resultSet.getString("rol"),
                resultSet.getString("descriere_rol")
        );

    }
}
