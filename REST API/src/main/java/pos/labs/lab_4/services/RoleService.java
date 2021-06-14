package pos.labs.lab_4.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pos.labs.lab_4.interfaces.IRolesService;
import pos.labs.lab_4.mappers.RoleRowMapper;
import pos.labs.lab_4.models.Role;
import pos.labs.lab_4.models.User;

import java.util.List;

@Service
public class RoleService implements IRolesService {

    private JdbcTemplate jdbcTemplate;
    private RoleRowMapper roleRowMapper;

    public RoleService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.roleRowMapper = new RoleRowMapper();
    }

    @Override
    public List<Role> getAllRoles() {
        String query = "SELECT * FROM roles";
        List<Role> roleList = jdbcTemplate.query(query, roleRowMapper);

        return roleList;
    }

    @Override
    public Role getRoleByID(Integer id) {
        String query = "SELECT * FROM roles WHERE role_id=?";
        List<Role> roleList = jdbcTemplate.query(query, roleRowMapper,id);
        if(roleList.size() == 0){
            return null;
        }else{
            return roleList.get(0);
        }
    }
}
