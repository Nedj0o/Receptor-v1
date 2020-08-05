package prep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prep.model.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,String> {
}
