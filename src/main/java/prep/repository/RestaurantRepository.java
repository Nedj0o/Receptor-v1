package prep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prep.model.entity.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {
}
