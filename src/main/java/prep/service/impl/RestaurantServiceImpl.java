package prep.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import prep.model.entity.Restaurant;
import prep.model.entity.User;
import prep.model.service.RestaurantServiceModel;
import prep.model.view.RestaurantViewModel;
import prep.repository.RestaurantRepository;
import prep.repository.UserRepository;
import prep.service.RestaurantService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public RestaurantServiceModel add(RestaurantServiceModel restaurantServiceModel){

        Restaurant restaurant = this.modelMapper
                .map(restaurantServiceModel,Restaurant.class);

        return this.modelMapper
                .map(this.restaurantRepository.saveAndFlush(restaurant),
                        RestaurantServiceModel.class);
    }

    @Override
    public void addRestaurant(RestaurantServiceModel restaurantServiceModel,String id) {

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

    @Override
    public void rate(User user, Restaurant restaurant, int rate) {
        Restaurant restaurantRated = this.restaurantRepository.findById(restaurant.getId()).orElse(null);
        restaurantRated.setStars(rate);
        this.restaurantRepository.saveAndFlush(restaurantRated);
    }

    @Override
    public Restaurant getById(String id) {
        return this.restaurantRepository
                .findById(id)
                .map(p -> this.modelMapper.map(p, Restaurant.class))
                .orElse(null);
    }

    @Override
    public RestaurantServiceModel findByName(String name) {
        return this.restaurantRepository.findByName(name)
                .map(restaurant -> this.modelMapper.map(restaurant, RestaurantServiceModel.class))
                .orElse(null);
    }
}
