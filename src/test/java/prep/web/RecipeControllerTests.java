package prep.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import prep.model.entity.*;
import prep.repository.RecipeRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RecipeRepository mockedRecipeRepository;
    private String RECIPE_ID;
    private String RECIPE_name = "Happy";
    private String RECIPE_DESCRIPTION = "Street 1 left righdfsdfsdt up down";
    private Category RECIPE_category = new Category();
    private User RECIPE_COMMITER = new User();
    private int RECIPE_LIKES = 0;

    @BeforeEach
    public void setUp() {
        RECIPE_category.setCategoryName(CategoryName.DESSERT);
        User user = new User();
        Recipe recipe = new Recipe();
        recipe.setName(RECIPE_name);
        recipe.setId(RECIPE_ID);
        recipe.setDescription(RECIPE_DESCRIPTION);

    }

    @AfterEach
    public void tearDown(){
        this.mockedRecipeRepository.deleteAll();
    }

    @Test
    public void testAddPageStatusCodeOk() throws Exception {
        this.mockMvc.perform(get("/recipes/add")).andExpect(status().isOk());
    }
}
