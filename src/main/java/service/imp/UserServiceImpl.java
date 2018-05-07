package service.imp;

import common.constants.UserRole;
import common.utils.PasswordUtil;
import dao.ImageDao;
import dao.UserDao;
import model.User;
import model.UserProfile;
import service.UserService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl extends BaseServiceImpl implements UserService {
    private UserDao userDao;
    private ImageDao imageDao;

    /* ===================================================== */

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public ImageDao getImageDao() {
        return imageDao;
    }

    public void setImageDao(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    /* ===================================================== */

    @Override
    public boolean register(UserProfile registerInfo) {
        String email = registerInfo.getEmail();
        if(this.userDao.getUserByEmail(email) != null) {
            return false;
        }
        User newUser = new User();
        Map userProfile = new HashMap();

        userProfile.put("nickname", registerInfo.getNickName());
        userProfile.put("gender", registerInfo.getGender());
        userProfile.put("mobile", registerInfo.getMobile());
        userProfile.put("age", registerInfo.getAge());
        //userProfile.put("deliveryAddress", new ArrayList());

        String profileID = this.userDao.saveUserProfileInMongo(userProfile);

        newUser.setProfileID(profileID);
        newUser.setEmail(email);
        newUser.setPassword(PasswordUtil.getEncryptedPassword(registerInfo.getPlainPassword()));
        newUser.setRole(UserRole.COMMON);
        newUser.setImageID("");
        this.userDao.save(newUser);

        return true;
    }

    @Override
    public boolean checkEmailAvailable(String email) {
        // 检查email是否已被注册，未注册（可用）返回true
        if(this.userDao.getUserByEmail(email) != null) {
            // 已被注册
            return false;
        }
        return true;
    }

    @Override
    public Map login(String email, String plainPassword) {
        Boolean logined = isLogined();
        if(!logined){
            if(email != null){
                User userInfo = this.userDao.getUserByEmail(email);
                if(userInfo != null){
                    if(PasswordUtil.checkPassword(plainPassword,userInfo.getPassword())){
                        setLoginedUserInfo(userInfo);
                        logined = true;
                    }
                }
            }
        }

        User userInfo = getLoginedUserInfo();
        Map params = new HashMap();
        if(logined){
            params.put("success",true);
            params.put("message","登录成功");
            params.put("userID",userInfo.getUserID());
            params.put("email",userInfo.getEmail());
            params.put("role",(userInfo.getRole() == UserRole.ADMIN) ? 0 : 1);
        }
        else{
            params.put("success",false);
            params.put("message","用户名或密码错误");
        }
        return params;
    }

    @Override
    public boolean logout() {
        Boolean logined = isLogined();
        if (logined) {
            getHttpSession().clear();
        }
        return true;
    }

    @Override
    public UserProfile showUserProfile() {
        User user = getLoginedUserInfo();
        if(user == null) return null;
        int userID = user.getUserID();
        return this.userDao.getUserProfile(userID);
    }

    @Override
    public boolean updatePassword(String oldPlainPassword, String newPlainPassword) {
        User user = getLoginedUserInfo();    // updateUserProfile已修复，现能确保session中的userInfo的信息与数据库保持一致
        if(PasswordUtil.checkPassword(oldPlainPassword, user.getPassword())) {
            user.setPassword(PasswordUtil.getEncryptedPassword(newPlainPassword));
            this.userDao.update(user);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updateUserPicture(File userPicture) {
        User user = this.getLoginedUserInfo();
        if(user.getImageID() != null && !user.getImageID().equals("")) {
            this.imageDao.deleteImageById(user.getImageID());
        }
        String newImageID = this.imageDao.saveImage(userPicture);
        user.setImageID(newImageID);
        this.userDao.update(user);
        return true;
    }

    @Override
    public boolean updateUserProfile(UserProfile newUserProfile) {
        int userID = this.getLoginedUserInfo().getUserID();
        UserProfile userProfile = this.userDao.getUserProfile(userID);
        userProfile.setNickName(newUserProfile.getNickName());
        userProfile.setGender(newUserProfile.getGender());
        userProfile.setMobile(newUserProfile.getMobile());
        userProfile.setAge(newUserProfile.getAge());
        boolean result = this.userDao.updateUserProfile(userProfile);
        if(result) {
            setLoginedUserInfo(this.userDao.getUserById(userID));
            return true;
        }
        else {
            return false;
        }
    }
}
