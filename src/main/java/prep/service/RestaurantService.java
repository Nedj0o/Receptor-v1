package prep.service;

import prep.model.service.RestaurantServiceModel;
import prep.model.view.RestaurantViewModel;

import java.util.List;

public interface RestaurantService {
    void addRestaurant(RestaurantServiceModel restaurantServiceModel);

    List<RestaurantViewModel> findAllItems();

    RestaurantViewModel findById(String id);

    void delete(String id);
}
