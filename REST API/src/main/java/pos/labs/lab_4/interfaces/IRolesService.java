package pos.labs.lab_4.interfaces;
import pos.labs.lab_4.models.Role;

import java.util.List;

public interface IRolesService {
    List<Role> getAllRoles();

    Role getRoleByID(Integer id);
}
