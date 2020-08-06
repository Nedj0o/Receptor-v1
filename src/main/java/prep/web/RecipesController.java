package prep.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prep.model.binding.RecipeAddBindingModel;
import prep.model.entity.Recipe;
import prep.model.entity.User;
import prep.model.service.RecipeServiceModel;
import prep.service.RecipeService;
import prep.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/recipes")
public class RecipesController {

    private final RecipeService recipeService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public RecipesController(RecipeService recipeService, ModelMapper modelMapper, UserService userService) {
        this.recipeService = recipeService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/add")
    public String add(Model model){

        if(!model.containsAttribute("recipeAddBindingModel")){
            model.addAttribute("recipeAddBindingModel", new RecipeAddBindingModel());
        }

        return "add-recipe";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("recipeAddBindingModel")
                                         RecipeAddBindingModel recipeAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             HttpSession httpSession){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("recipeAddBindingModel", recipeAddBindingModel);
            redirectAttributes.addFlashAttribute
                    ("org.springframework.validation.BindingResult.recipeAddBindingModel",bindingResult);
            return "redirect:add";
        }


        this.recipeService.addRecipe(this.modelMapper
                .map(recipeAddBindingModel, RecipeServiceModel.class),httpSession.getAttribute("id").toString());

        return "redirect:/";
    }

    @GetMapping("/details")
    public ModelAndView details(@RequestParam("id") String id, ModelAndView modelAndView) {

        modelAndView.addObject("recipe",this.recipeService.findById(id));
        modelAndView.setViewName("details-recipe");
        return modelAndView;
    }

    @GetMapping("/most")
    public ModelAndView mostliked(@RequestParam("id") String id, ModelAndView modelAndView){
        modelAndView.addObject("recipe",this.recipeService.findMostLiked(id));
        modelAndView.setViewName("most-liked-recipe");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")String id){
        Recipe recipe = this.recipeService.getById(id);
        this.userService.delete(this.modelMapper.map(recipe, RecipeServiceModel.class));
        this.recipeService.delete(id);


        return "redirect:/";
    }

    @GetMapping("/voteUp")
    public ModelAndView voteUp(@RequestParam(name = "id") String id, ModelAndView modelAndView, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        Recipe recipe = this.recipeService.getById(id);
        this.recipeService.like(user, recipe, 1);

        User u = (User) httpSession.getAttribute("user");
        User user2 = this.userService.getById(u.getId());
        if(user2.getRole().getRoleName().toString().equals("ADMIN")){
            modelAndView.addObject("isADMIN",true);
        }
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

}
