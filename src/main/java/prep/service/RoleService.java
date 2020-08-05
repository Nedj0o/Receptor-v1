package prep.service;

import prep.model.entity.RoleName;
import prep.model.service.RoleServiceModel;

public interface RoleService {
    void init();
    RoleServiceModel getByRoleName(RoleName roleName);
}
