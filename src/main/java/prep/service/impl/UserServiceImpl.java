package prep.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import prep.model.binding.UserChangeRoleBindModel;
import prep.model.entity.Recipe;
import prep.model.entity.Role;
import prep.model.entity.RoleName;
import prep.model.entity.User;
import prep.model.service.RecipeServiceModel;
import prep.model.service.UserServiceModel;
import prep.model.view.UserViewModel;
import prep.repository.UserRepository;
import prep.service.RoleService;
import prep.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleService roleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }


    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = this.modelMapper
                .map(userServiceModel, User.class);

        return this.modelMapper
                .map(this.userRepository.saveAndFlush(user),
                        UserServiceModel.class);

    }

    @Override
    public UserServiceModel findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public User getById(String id) {
        return this.userRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public void changeRole(UserChangeRoleBindModel userChangeRoleBindModel) {
        User user = this.userRepository
                .findByUsername(userChangeRoleBindModel.getUsername())
                .orElse(null);

        user.setRole(this.modelMapper.map(this.roleService.getByRoleName(userChangeRoleBindModel.getRoleName()), Role.class));
        this.userRepository.save(user);
    }

    @Override
    public UserServiceModel findbyEmail(String email) {
        return this.userRepository
                .findByEmail(email)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        if (this.userRepository.count() == 0) {
            userServiceModel.setRoleServiceModel(this.roleService.getByRoleName(RoleName.ADMIN));
        } else {
            userServiceModel.setRoleServiceModel(this.roleService.getByRoleName(RoleName.USER));
        }
        User user = this.modelMapper.map(userServiceModel, User.class);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserViewModel> getAll() {
        List<User> users = this.userRepository.findAll();
        List<UserViewModel> userViewModels = new ArrayList<>();
        for (User u : users) {
            userViewModels.add(this.modelMapper.map(u, UserViewModel.class));
        }
        return userViewModels;
    }

    @Override
    public List<User> searchUsersByAlikeNicknames(String searchString) {
        List<User> byNicknameStartingWith = this.userRepository.findByUsernameStartingWith(searchString);
        return byNicknameStartingWith;
    }

    @Override
    public void delete(RecipeServiceModel recipeServiceModel) {
        User user = this.userRepository.findByUsername(recipeServiceModel.getCommiter().getUsername()).orElse(null);
        Recipe recipe = this.modelMapper.map(recipeServiceModel, Recipe.class);

        for (int i = 0; i < user.getRecipes().size(); i++) {
            if (user.getRecipes().get(i).getId().equals(recipe.getId())) {
                user.getRecipes().remove(i);
                break;
            }
        }

        this.userRepository.saveAndFlush(user);
    }
}
