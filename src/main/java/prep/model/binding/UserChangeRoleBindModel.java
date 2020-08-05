package prep.model.binding;

import org.hibernate.validator.constraints.Length;
import prep.model.entity.RoleName;

import javax.validation.constraints.NotNull;

public class UserChangeRoleBindModel {
    private String username;
    private RoleName roleName;

    public UserChangeRoleBindModel() {
    }

    public UserChangeRoleBindModel(String nickname, RoleName roleName) {
        this.username = nickname;
        this.roleName = roleName;
    }

    @Length(min = 4, max = 18, message = "The nickname must be between 4 and 18 symbols.")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "Role not selected!")
    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
