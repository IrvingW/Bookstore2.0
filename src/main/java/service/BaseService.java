package service;

import model.User;

import java.util.Map;

public interface BaseService {
    Map<String,Object> getHttpSession();
    boolean isLogined();
    User getLoginedUserInfo();
    void setLoginedUserInfo(User userInfo);
    void clearLoginedUserInfo();

}
