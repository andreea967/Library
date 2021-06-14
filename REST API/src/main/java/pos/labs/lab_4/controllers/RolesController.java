package pos.labs.lab_4.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pos.labs.lab_4.interfaces.IRolesService;
import pos.labs.lab_4.models.Role;

import java.util.List;

@RestController()
public class RolesController {
    private final IRolesService rolesService;

    public RolesController(IRolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping("api/roles")
    public ResponseEntity<?> getRoles(){
        List<Role> roleList = rolesService.getAllRoles();
        return ResponseEntity.status(200).body(roleList);
    }

    @GetMapping("api/roles/{id}")
    public ResponseEntity<?> getRoleByID(@PathVariable Integer id){
        Role role = rolesService.getRoleByID(id);
        if(role == null){
            return ResponseEntity.status(404).body(null);
        }else{
            return ResponseEntity.status(200).body(role);
        }
    }
}
