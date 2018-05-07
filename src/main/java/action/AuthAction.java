package action;

import com.opensymphony.xwork2.ActionSupport;
import model.UserProfile;
import service.LoginModule;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

public class AuthAction extends ActionSupport {
    private UserService userService;
    private LoginModule loginModule;

    private Map params;
    private UserProfile registerInfo;

    private String email;
    private String password;
    private String confirmPassword;

    /* ======================================================== */

    public LoginModule getLoginModule() {
        return loginModule;
    }

    public void setLoginModule(LoginModule loginModule) {
        this.loginModule = loginModule;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public UserProfile getRegisterInfo() {
        return registerInfo;
    }

    public void setRegisterInfo(UserProfile registerInfo) {
        this.registerInfo = registerInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /* ======================================================== */

    public String register(){
        boolean result = this.userService.register(registerInfo);
        this.params = new HashMap();
        if(result) {
            this.params.put("success", true);
        }
        else {
            this.params.put("success", false);
        }
        return "ajax";
    }

    public String checkEmailAvailable(){
        this.params = new HashMap();
        if(this.userService.checkEmailAvailable(this.email)) {
            params.put("success", true);
        }
        else {
            params.put("success", false);
        }
        return "ajax";
    }

    public String login(){
        this.params = this.userService.login(this.email, this.password);
        return "ajax";
    }

    public String logout(){
        this.userService.logout();
        return "logout";
    }
}
