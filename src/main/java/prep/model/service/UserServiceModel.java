package prep.model.service;

public class UserServiceModel extends BaseServiceModel {
    private String username;
    private String password;
    private String email;
    private RoleServiceModel roleServiceModel;

    public UserServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleServiceModel getRoleServiceModel() {
        return roleServiceModel;
    }

    public void setRoleServiceModel(RoleServiceModel roleServiceModel) {
        this.roleServiceModel = roleServiceModel;
    }
}
