package prep.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prep.model.binding.SearchUsersBindModel;
import prep.model.binding.UserLoginBindingModel;
import prep.model.binding.UserRegisterBindingModel;
import prep.model.entity.User;
import prep.model.service.UserServiceModel;
import prep.model.view.UserViewModel;
import prep.service.FollowerService;
import prep.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final FollowerService followerService;

    public UsersController(UserService userService, ModelMapper modelMapper, FollowerService followerService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.followerService = followerService;
    }

    @GetMapping("/register")
    public ModelAndView getRegister(@Valid
                                    @ModelAttribute("userRegisterBindModel") UserRegisterBindingModel userRegisterBindModel,
                                    HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.addObject("userRegisterBindModel", userRegisterBindModel);

        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView postRegister(@Valid
                                     @ModelAttribute("userRegisterBindModel") UserRegisterBindingModel userRegisterBindModel,
                                     BindingResult bindingResult, ModelAndView modelAndView, HttpSession httpSession,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() ||
                !userRegisterBindModel.getPassword().equals(userRegisterBindModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindModel", userRegisterBindModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegBindModel", bindingResult);
            modelAndView.setViewName("register");
        } else {
            if (!userRegisterBindModel.getConfirmPassword().equals(userRegisterBindModel.getPassword())) {
                redirectAttributes.addFlashAttribute("userRegisterBindModel", userRegisterBindModel);
                redirectAttributes.addFlashAttribute("pNoMatch", true);
                modelAndView.setViewName("register");
            } else {
                if (this.userService.findByUsername(userRegisterBindModel.getUsername()) != null) {
                    redirectAttributes.addFlashAttribute("userRegisterBindModel", userRegisterBindModel);
                    redirectAttributes.addFlashAttribute("nickTaken", true);
                    modelAndView.setViewName("register");
                } else if (this.userService.findbyEmail(userRegisterBindModel.getEmail()) != null) {
                    redirectAttributes.addFlashAttribute("userRegisterBindModel", userRegisterBindModel);
                    redirectAttributes.addFlashAttribute("emailTaken", true);
                    modelAndView.setViewName("register");
                } else {

                    UserServiceModel userServiceModel = this.modelMapper.map(userRegisterBindModel, UserServiceModel.class);
                    this.userService.registerUser(userServiceModel);
                    modelAndView.setViewName("redirect:/users/login");
                }
            }
        }
        return modelAndView;
    }

    @GetMapping("/login")
    public String getLogin(Model model, HttpSession httpSession) {
        if (model.getAttribute("userLoginBindModel") == null) {
            model.addAttribute("userLoginBindModel", new UserLoginBindingModel());
        }

        return "login";
    }

    @PostMapping("/login")
    public ModelAndView postLogin(@Valid
                                  @ModelAttribute("userLoginBindModel") UserLoginBindingModel userLoginBindModel,
                                  BindingResult bindingResult, ModelAndView modelAndView, HttpSession httpSession,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindModel", userLoginBindModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindModel", bindingResult);
            modelAndView.setViewName("login");
        } else {
            UserServiceModel userServiceModel = this.userService.findByUsername(userLoginBindModel.getUsername());

            if (userServiceModel == null || !userServiceModel.getPassword().equals(userLoginBindModel.getPassword())) {
                redirectAttributes.addFlashAttribute("notFound", true);
                redirectAttributes.addFlashAttribute("userLoginBindModel", userLoginBindModel);
                modelAndView.setViewName("login");
            } else {
                httpSession.setAttribute("user", this.modelMapper.map(userServiceModel, User.class));
                httpSession.setAttribute("id", userServiceModel.getId());
                modelAndView.setViewName("redirect:/");
            }
        }

        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView getLogout(ModelAndView modelAndView, HttpSession httpSession) {
        httpSession.invalidate();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView getDetails(@RequestParam("id") String id, ModelAndView modelAndView, HttpSession httpSession) {
        User user = this.userService.getById(id);
        Collections.reverse(user.getRecipes());
        //reverses the list so the newest recipes can be on the top

        if (this.followerService.getFollowers(user) != null) {
            modelAndView.addObject("hasFollowers", true);
        }

        User userLogged = (User) httpSession.getAttribute("user");
        if (userLogged.getId().equals(user.getId())) {
            modelAndView.addObject("isOwner", true);
        } else {
            modelAndView.addObject("isOwner", false);

            modelAndView.addObject("unfollow", false);
            List<User> followers = this.followerService.getFollowers(user);
            for (User f : followers) {
                if (f.getId().equals(userLogged.getId())) {
                    modelAndView.addObject("unfollow", true);
                    break;
                }
            }
            //if the current session user followed the profile's user, the button follow becomes unfollow
        }
        //Only the owner of the posts can delete them.

        User u = (User) httpSession.getAttribute("user");
        User user2 = this.userService.getById(u.getId());
        if (user2.getRole().getRoleName().toString().equals("ADMIN")) {
            modelAndView.addObject("isADMIN", true);
        }

        modelAndView.addObject("user", user);
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @GetMapping("/follow/{id}")
    public ModelAndView follow(@PathVariable(name = "id") String id, ModelAndView modelAndView, HttpSession httpSession) {
        User userToFollow = this.userService.getById(id);
        this.followerService.follow(httpSession.getAttribute("id").toString(), userToFollow.getId());
        modelAndView.setViewName("redirect:/users/profile/?id=" + userToFollow.getId());

        User u = (User) httpSession.getAttribute("user");
        User user = this.userService.getById(u.getId());
        if (user.getRole().getRoleName().toString().equals("ADMIN")) {
            modelAndView.addObject("isADMIN", true);
        }

        return modelAndView;
    }

    @GetMapping("/unfollow/{id}")
    public ModelAndView unfollow(@PathVariable(name = "id") String id, ModelAndView modelAndView, HttpSession httpSession) {
        User user = this.userService.getById(id);
        this.followerService.unfollow(httpSession.getAttribute("id").toString(), user.getId());
        modelAndView.setViewName("redirect:/users/profile/?id=" + user.getId());
        return modelAndView;
    }

    @GetMapping("/followers/{id}")
    public ModelAndView followers(@PathVariable(name = "id") String id, ModelAndView modelAndView, HttpSession httpSession) {
        User user = this.userService.getById(id);

        modelAndView.addObject("followersById", this.followerService.getFollowers(user));
        modelAndView.addObject("user", user);

        User u = (User) httpSession.getAttribute("user");
        User user2 = this.userService.getById(u.getId());
        if (user2.getRole().getRoleName().toString().equals("ADMIN")) {
            modelAndView.addObject("isADMIN", true);
        }

        modelAndView.setViewName("followers");
        return modelAndView;
    }

    @GetMapping("/search")
    public String searchForUsersPage(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("id") != null) {
            User u = (User) httpSession.getAttribute("user");
            User user = this.userService.getById(u.getId());
            if (user.getRole().getRoleName().toString().equals("ADMIN")) {
                model.addAttribute("isADMIN", true);
                List<UserViewModel> all = this.userService.getAll();
                model.addAttribute("allUsers", all);
            }

            model.addAttribute("searchUsersBindModel", new SearchUsersBindModel());
            return "search-users-page";
        } else {
            return "unauthorized";
        }
    }

    @GetMapping("/search/result")
    public ModelAndView searchForUsers(@Valid
                                       @ModelAttribute("searchUsersBindModel") SearchUsersBindModel searchUsersBindModel,
                                       ModelAndView modelAndView, HttpSession httpSession,
                                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (httpSession.getAttribute("id") == null) {
            modelAndView.setViewName("unauthorized");
        } else if (!bindingResult.hasErrors()) {
            List<User> usersList = this.userService
                    .searchUsersByAlikeNicknames(searchUsersBindModel.getSearchString());
            modelAndView.addObject("usersList", usersList);

            User u = (User) httpSession.getAttribute("user");
            User user = this.userService.getById(u.getId());
            if (user.getRole().getRoleName().toString().equals("ADMIN")) {
                modelAndView.addObject("isADMIN", true);
            }

            modelAndView.setViewName("search-users");
        } else {
            redirectAttributes.addFlashAttribute("searchUsersBindModel", searchUsersBindModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.searchUsersBindModel", bindingResult);
            modelAndView.setViewName("search-users-page");
        }

        User u = (User) httpSession.getAttribute("user");
        User user = this.userService.getById(u.getId());
        if (user.getRole().getRoleName().toString().equals("ADMIN")) {
            modelAndView.addObject("isADMIN", true);
        }

        return modelAndView;
    }
}
