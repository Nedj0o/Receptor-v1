package prep.service;

import prep.model.entity.User;

import java.util.List;

public interface FollowerService {
    void follow(String followerId, String followedId);
    void unfollow(String followerId, String followedId);
    List<User> getFollowers(User user);
}
