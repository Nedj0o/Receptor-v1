package prep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prep.model.entity.Restaurant;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {
    Optional<Restaurant> findByName(String name);
}
