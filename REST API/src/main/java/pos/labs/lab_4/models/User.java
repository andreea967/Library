package pos.labs.lab_4.models;

public class User {
    public Integer uid;
    public String username ;
    public String password ;
    public Integer roleId ;


    public User(Integer uid, String username, String password, Integer roleId) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }

}
