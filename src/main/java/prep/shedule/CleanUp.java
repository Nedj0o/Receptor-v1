package prep.shedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import prep.service.RecipeService;

@Component
public class CleanUp {
    private final RecipeService recipeService;

    @Autowired
    public CleanUp(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Scheduled(fixedDelay = 5000)
    public void cleanUpOldAnnouncements() {
        this.recipeService.cleanUpOldRecipe();
    }
}
