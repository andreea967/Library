package pos.labs.lab_4.models;

public class UserLoginResponse {
    public Integer uid;
    public String username ;
    public Integer roleId ;


    public UserLoginResponse(Integer uid, String username, Integer roleId) {
        this.uid = uid;
        this.username = username;
        this.roleId = roleId;
    }
}
