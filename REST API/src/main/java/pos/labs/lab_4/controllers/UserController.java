package pos.labs.lab_4.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.labs.lab_4.interfaces.IUserService;
import pos.labs.lab_4.models.User;
import pos.labs.lab_4.models.UserLogin;
import pos.labs.lab_4.models.UserLoginResponse;
import pos.labs.lab_4.services.UserService;

import java.util.List;

// localhost:7766/
@RestController()
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("api/users/hello")
    public ResponseEntity<String> getHello() {
        return ResponseEntity.status(200).body(" Hello world ^ ^ ");
    }

    @GetMapping("api/users")
    public ResponseEntity<?> getUsers(){
        List<User> userList = userService.getAllUsers();

        return ResponseEntity.status(200).body(userList);
    }

    @GetMapping("api/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id){
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.status(200).body(user);
    }

    @RequestMapping(value = "api/users/", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User user){
        try{
            Integer id = userService.addUser(user);
            return ResponseEntity.status(201).body(id);
        }catch(DuplicateKeyException e){
            return ResponseEntity.status(401).body("username exists");
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(401).body("role_id invalid");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @RequestMapping(value = "api/auth/", method = RequestMethod.GET)
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password){
        UserLogin userLogin = new UserLogin(username, password);
        User user = userService.authenticate(userLogin);
        if(user == null){
            return ResponseEntity.status(404).body(null);
        }
        UserLoginResponse userLoginResponse = new UserLoginResponse(user.uid, user.username, user.roleId);
        return ResponseEntity.status(200).body(userLoginResponse);
    }

    @DeleteMapping("api/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.status(200).body(null);
    }

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user){
        try{
            user.uid = id;
            // incercam sa inseram
            userService.addUserId(user);
            return ResponseEntity.status(201).body(id);
        }catch(DuplicateKeyException e){
                // am intrat aici, inseamna ca user-ul cu uid dat exista, asa ca il updatam
                try{
                    userService.updateUser(user);
                    return ResponseEntity.status(200).body(null);
                }
                catch(DuplicateKeyException e1){
                    return ResponseEntity.status(401).body("username exists");
                }
                catch(DataIntegrityViolationException e1) {
                    return ResponseEntity.status(401).body("role_id invalid");
                } catch(Exception e1){
                    return ResponseEntity.status(500).body(e.getMessage());
                }
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(401).body("role_id invalid");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
