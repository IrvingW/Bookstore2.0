package service.imp;

import common.constants.UserRole;
import common.utils.PasswordUtil;
import dao.BookDao;
import dao.UserDao;
import model.User;
import service.AdminService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServiceImpl extends BaseServiceImpl implements AdminService {
    private UserDao userDao;
    private BookDao bookDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<User> showAllUserList() {
        List<User> userList = this.userDao.getAllUsers();
        return userList;
    }

    @Override
    public boolean addUser(String email, String password) {
        if(this.userDao.getUserByEmail(email) != null) {
            return false;
        }
        User user = new User();
        Map userProfile = new HashMap();

        userProfile.put("name", "");
        userProfile.put("gender", "");
        userProfile.put("mobile", "");

        String profileID = this.userDao.saveUserProfileInMongo(userProfile);
        user.setProfileID(profileID);
        user.setEmail(email);
        user.setImageID("");
        user.setPassword(PasswordUtil.getEncryptedPassword(password));
        user.setRole(UserRole.COMMON);

        return this.userDao.save(user);
    }

    @Override
    public boolean deleteUser(int userID) {
        User user = this.userDao.getUserById(userID);
        return this.userDao.deleteUser(user);
    }

    @Override
    public boolean updateUser(int userID, String email, String password) {
        User user = this.userDao.getUserById(userID);
        if(user == null){
            return false;
        }
        user.setEmail(email);
        user.setPassword(PasswordUtil.getEncryptedPassword(password));

        return this.userDao.update(user);
    }

    @Override
    public User getOldUserInfo(int userID) {
        return this.userDao.getUserById(userID);
    }
}
