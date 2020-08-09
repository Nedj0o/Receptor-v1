package prep.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import prep.model.entity.Restaurant;
import prep.repository.RestaurantRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantServiceTests {

    private Restaurant restaurantTest;
    private RestaurantRepository mockedRestaurantRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        this.restaurantTest = new Restaurant() {{
            setId("696969699696969699696");
            setName("Happy");
            setAddress("left right up down street");
            setCategory("Family-House");
            setStars(0);
        }};

        this.mockedRestaurantRepository = Mockito.mock(RestaurantRepository.class);
    }

    @Test
    public void restaurantService_GetWithCorrectName_ShouldReturnCorrect() {

    }
}


