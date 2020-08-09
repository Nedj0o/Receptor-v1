package prep.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import prep.model.entity.Restaurant;
import prep.repository.RestaurantRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RestaurantRepository mockedRestaurantRepository;
    private String RESTAURANT_ID;
    private String RESTAURANT_name = "Happy";
    private String RESTAURANT_address = "Street 1 left right up down";
    private String RESTAURANT_category = "Family-House";
    private int RESTAURANT_stars = 0;

    @BeforeEach
    public void setUp() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(RESTAURANT_name);
        restaurant.setAddress(RESTAURANT_address);
        restaurant.setCategory(RESTAURANT_category);
        restaurant.setStars(RESTAURANT_stars);
        restaurant = this.mockedRestaurantRepository.saveAndFlush(restaurant);
        RESTAURANT_ID = restaurant.getId();
    }

    @AfterEach
    public void tearDown(){
        this.mockedRestaurantRepository.deleteAll();
    }

    @Test
    public void testAddPageStatusCodeOk() throws Exception {
        this.mockMvc.perform(get("/restaurants/add")).andExpect(status().isOk());
    }

}
