package prep.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import prep.model.entity.Restaurant;
import prep.model.service.RestaurantServiceModel;
import prep.model.view.RestaurantViewModel;
import prep.repository.RestaurantRepository;
import prep.service.RestaurantService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
    }

    public RestaurantServiceModel add(RestaurantServiceModel restaurantServiceModel){

        Restaurant restaurant = this.modelMapper
                .map(restaurantServiceModel,Restaurant.class);

        return this.modelMapper
                .map(this.restaurantRepository.saveAndFlush(restaurant),
                        RestaurantServiceModel.class);
    }

    @Override
    public void addRestaurant(RestaurantServiceModel restaurantServiceModel) {
        Restaurant restaurant = this.modelMapper
                .map(restaurantServiceModel,Restaurant.class);

        this.restaurantRepository.saveAndFlush(restaurant);
    }

    @Override
    public List<RestaurantViewModel> findAllItems() {
        return this.restaurantRepository.findAll()
                .stream()
                .map(restaurant -> {
                    RestaurantViewModel restaurantViewModel = this.modelMapper
                            .map(restaurant,RestaurantViewModel.class);

                    restaurantViewModel.setImgUrl(String
                            .format("/img/%s.jpg",
                                    restaurant.getCategory()));

                    return restaurantViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantViewModel findById(String id) {
        return this.restaurantRepository
                .findById(id)
                .map(restaurant -> {
                    RestaurantViewModel restaurantViewModel = this.modelMapper
                            .map(restaurant,RestaurantViewModel.class);

                    restaurantViewModel.setImgUrl(String
                            .format("/img/%s.jpg",
                                    restaurant.getCategory()));

                    return restaurantViewModel;
                })
                .orElse(null);
    }

    @Override
    public void delete(String id) {
        this.restaurantRepository
                .deleteById(id);
    }
}
