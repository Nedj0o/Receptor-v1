package prep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prep.model.entity.FollowedUser;

@Repository
public interface FollowerRepository extends JpaRepository<FollowedUser, String> {
    FollowedUser findByFollowed_Id(String followedId);
    void deleteById(String id);
}
