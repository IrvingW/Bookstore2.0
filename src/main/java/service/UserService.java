package service;

import model.User;
import model.UserProfile;

import java.io.File;
import java.util.Map;

public interface UserService extends BaseService {
    boolean register(UserProfile registerInfo);
    boolean checkEmailAvailable(String email);
    Map login(String email, String plainPassword);
    boolean logout();
    UserProfile showUserProfile();
    boolean updatePassword(String oldPlainPassword, String newPlainPassword);
    boolean updateUserProfile(UserProfile newUserProfile);
    boolean updateUserPicture(File userPicture);
}
