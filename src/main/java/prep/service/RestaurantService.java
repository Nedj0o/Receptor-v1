package prep.service;

import prep.model.entity.Restaurant;
import prep.model.entity.User;
import prep.model.service.RestaurantServiceModel;
import prep.model.view.RestaurantViewModel;

import java.util.List;

public interface RestaurantService {
    void addRestaurant(RestaurantServiceModel restaurantServiceModel, String id);

    List<RestaurantViewModel> findAllItems();

    RestaurantViewModel findById(String id);

    void delete(String id);

    void rate(User user, Restaurant restaurant, int rate);

    Restaurant getById(String id);
}
