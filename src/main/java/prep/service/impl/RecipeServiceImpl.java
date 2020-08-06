package prep.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import prep.model.entity.Recipe;
import prep.model.entity.User;
import prep.model.service.RecipeServiceModel;
import prep.model.view.RecipeViewModel;
import prep.repository.RecipeRepository;
import prep.repository.UserRepository;
import prep.service.CategoryService;
import prep.service.RecipeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository, CategoryService categoryService, ModelMapper modelMapper, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;

    }

    @Override
    public void addRecipe(RecipeServiceModel recipeServiceModel, String id) {


        Recipe recipe = this.modelMapper
                .map(recipeServiceModel, Recipe.class);
        User user = this.userRepository.findById(id).orElse(null);

        recipe.setCategory(this.categoryService
                .findByCategoryName(recipeServiceModel.getCategory().getCategoryName()));
        recipe.setCommiter(user);

        if (user.getRecipes() == null) {
            List<Recipe> recipes = new ArrayList<>();
            recipes.add(recipe);
            user.setRecipes(recipes);
        } else {
            user.getRecipes().add(recipe);
        }


        this.userRepository.save(user);

    }

    @Override
    public List<RecipeViewModel> findAllItems() {
        return this.recipeRepository.findAll()
                .stream()
                .map(recipe -> {
                    RecipeViewModel recipeViewModel = this.modelMapper
                            .map(recipe, RecipeViewModel.class);

                    recipeViewModel.setImgUrl(String
                            .format("/img/%s.jpg",
                                    recipe.getCategory().getCategoryName().name()));

                    return recipeViewModel;
                })
                .collect(Collectors.toList());
    }



    @Override
    public void delete(String id) {
        this.recipeRepository
                .deleteById(id);
    }

    @Override
    public List<Recipe> getAll() {
        List<Recipe> recipes = this.recipeRepository.findAll();
        Collections.reverse(recipes);
        //reverse so the most recent post is on the top
        return recipes;
    }

    @Override
    public Recipe getById(String id) {
        return this.recipeRepository
                .findById(id)
                .map(p -> this.modelMapper.map(p, Recipe.class))
                .orElse(null);
    }

    @Override
    public RecipeViewModel findById(String id) {
        return this.recipeRepository
                .findById(id)
                .map(recipe -> {
                    RecipeViewModel recipeViewModel = this.modelMapper
                            .map(recipe, RecipeViewModel.class);

                    recipeViewModel.setImgUrl(String
                            .format("/img/%s.jpg",
                                    recipe.getCategory().getCategoryName().name()));

                    return recipeViewModel;
                }).orElse(null);
    }

    @Override
    public RecipeViewModel findMostLiked(String id) {

        List<RecipeViewModel> recipee = this.recipeRepository.findAll()
                .stream()
                .map(recipe -> {
                    RecipeViewModel recipeViewModel = this.modelMapper
                            .map(recipe, RecipeViewModel.class);

                    recipeViewModel.setImgUrl(String
                            .format("/img/%s.jpg",
                                    recipe.getCategory().getCategoryName().name()));

                    return recipeViewModel;
                })
                .collect(Collectors.toList());

        String mostLikesID="";
        int mostlikes=0;

         for (RecipeViewModel r : recipee) {
             int like = r.getLikes();
             String idd = r.getId();
             if(like>mostlikes){
                 mostlikes=like;
                 mostLikesID=idd;
             }
         }


        return findById(mostLikesID);
    }

    @Override
    public void like(User user, Recipe recipe, int vote) {
        Recipe recipeLiked = this.recipeRepository.findById(recipe.getId()).orElse(null);
        recipeLiked.setLikes(recipeLiked.getLikes() + 1);
        this.recipeRepository.saveAndFlush(recipeLiked);
    }

}
