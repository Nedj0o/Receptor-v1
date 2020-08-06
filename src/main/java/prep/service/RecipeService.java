package prep.service;

import prep.model.entity.Recipe;
import prep.model.entity.User;
import prep.model.service.RecipeServiceModel;
import prep.model.view.RecipeViewModel;

import java.util.List;

public interface RecipeService {
    void addRecipe(RecipeServiceModel recipeServiceModel, String id);

    List<RecipeViewModel> findAllItems();

    RecipeViewModel findById(String id);

    void delete(String id);

    List<Recipe> getAll();

    Recipe getById(String id);

    void like(User user, Recipe recipe, int vote);

    RecipeViewModel findMostLiked(String id);
}
