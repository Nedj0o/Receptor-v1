
package prep.model.service;


import prep.model.entity.RoleName;

public class RoleServiceModel extends BaseServiceModel{
    private RoleName roleName;

    public RoleServiceModel() {
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
