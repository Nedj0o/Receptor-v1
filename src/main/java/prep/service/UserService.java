package prep.service;

import prep.model.binding.UserChangeRoleBindModel;
import prep.model.entity.User;
import prep.model.service.RecipeServiceModel;
import prep.model.service.UserServiceModel;
import prep.model.view.UserViewModel;

import java.util.List;

public interface UserService {
    UserServiceModel register(UserServiceModel userServiceModel);

    UserServiceModel findByUsername(String username);

    User getById(String id);

    void changeRole(UserChangeRoleBindModel userChangeRoleBindModel);

    UserServiceModel findbyEmail(String email);

    void registerUser(UserServiceModel userServiceModel);

    List<UserViewModel> getAll();

    List<User> searchUsersByAlikeNicknames(String searchString);
    void delete(RecipeServiceModel recipeServiceModel);

}
