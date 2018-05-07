package dao;

import model.User;
import model.UserProfile;

import java.util.List;
import java.util.Map;

public interface UserDao extends BaseDao {
    User getUserById(int userID);
    User getUserByEmail(String email);
    UserProfile getUserProfile(int userID);
    boolean saveUserProfile(UserProfile newUserProfile);
    boolean updateUserProfile(UserProfile newUserProfile);
    String saveUserProfileInMongo(Map userProfile);
    boolean deleteUser(User user);
    List<User> getAllUsers();


}
