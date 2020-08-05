package prep.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import prep.model.entity.User;
import prep.service.RecipeService;
import prep.service.RestaurantService;
import prep.service.RoleService;
import prep.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final RecipeService recipeService;
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final RoleService roleService;

    public HomeController(RecipeService recipeService, RestaurantService restaurantService, UserService userService, RoleService roleService) {
        this.recipeService = recipeService;
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public ModelAndView index(HttpSession httpSession, ModelAndView modelAndView){
        this.roleService.init();



        if (httpSession.getAttribute("user") == null) {
            modelAndView.setViewName("index");
        } else {
            modelAndView.setViewName("home");
            modelAndView.addObject("allRecipes", this.recipeService.getAll());
            modelAndView.addObject("user", httpSession.getAttribute("user"));
            modelAndView.addObject("userId", httpSession.getAttribute("id"));

            User u = (User) httpSession.getAttribute("user");
            User user = this.userService.getById(u.getId());
            if(user.getRole().getRoleName().toString().equals("ADMIN")){
                modelAndView.addObject("isADMIN",true);
            }
        }
        return modelAndView;
    }

    @GetMapping("/recipes")
    public ModelAndView items(HttpSession httpSession, ModelAndView modelAndView){

        User u = (User) httpSession.getAttribute("user");
        User user = this.userService.getById(u.getId());
        if (user.getRole().getRoleName().toString().equals("ADMIN") ||
                user.getRole().getRoleName().toString().equals("USER")) {
            modelAndView.addObject("recipes",this.recipeService.findAllItems());
            modelAndView.setViewName("recipes");
        } else {
            modelAndView.setViewName("error");
        }

        return  modelAndView;
    }

    @GetMapping("/restaurants")
    public ModelAndView restaurants(HttpSession httpSession, ModelAndView modelAndView){

        User u = (User) httpSession.getAttribute("user");
        User user = this.userService.getById(u.getId());
        if (user.getRole().getRoleName().toString().equals("ADMIN") ||
                user.getRole().getRoleName().toString().equals("USER")) {
            modelAndView.addObject("restaurants",this.restaurantService.findAllItems());
            modelAndView.setViewName("restaurants");
        } else {
            modelAndView.setViewName("error");
        }

        return  modelAndView;
    }
}
