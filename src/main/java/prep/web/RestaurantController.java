package prep.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prep.model.binding.RestaurantAddBindingModel;
import prep.model.service.RestaurantServiceModel;
import prep.service.RestaurantService;

import javax.validation.Valid;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final ModelMapper modelMapper;

    public RestaurantController(RestaurantService restaurantService, ModelMapper modelMapper) {
        this.restaurantService = restaurantService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String add(Model model){

        if(!model.containsAttribute("restaurantAddBindingModel")){
            model.addAttribute("restaurantAddBindingModel", new RestaurantAddBindingModel());
        }

        return "add-restaurant";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("restaurantAddBindingModel")
                                         RestaurantAddBindingModel restaurantAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("restaurantAddBindingModel",restaurantAddBindingModel);
            redirectAttributes.addFlashAttribute
                    ("org.springframework.validation.BindingResult.restaurantAddBindingModel",bindingResult);
            return "redirect:add";
        }

        this.restaurantService.addRestaurant(this.modelMapper
                .map(restaurantAddBindingModel, RestaurantServiceModel.class));

        return "redirect:/";
    }

    @GetMapping("/details")
    public ModelAndView details(@RequestParam("id") String id, ModelAndView modelAndView) {

        modelAndView.addObject("restaurant",this.restaurantService.findById(id));
        modelAndView.setViewName("details-restaurant");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")String id){
        this.restaurantService.delete(id);
        return "redirect:/";
    }

}
