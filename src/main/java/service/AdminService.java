package service;

import model.User;

import java.util.List;

public interface AdminService extends BaseService {
    List<User> showAllUserList();
    User getOldUserInfo(int userID);
    boolean deleteUser(int userID);
    boolean addUser(String email,String password);
    boolean updateUser(int userID,String email,String password);
}
