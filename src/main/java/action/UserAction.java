package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import model.UserProfile;
import service.UserService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UserAction extends ActionSupport {
    private UserService userService;

    private Map params;
    private UserProfile userProfile;

    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    private File userPicture;
    private String userPictureFileName;
    private String userPictureContentType;

    /* ===================================================== */

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
    public File getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(File userPicture) {
        this.userPicture = userPicture;
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

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public UserProfile getUserProfile(){
        return this.userProfile;
    }

    /* ===================================================== */

    public String showUserProfile(){
        this.userProfile = this.userService.showUserProfile();
        ActionContext.getContext().put("userProfile",this.userProfile);
        return "showUserProfile";
    }

    public String updatePassword() {
        this.params = new HashMap();
        System.out.println("oldPassword: "+ oldPassword);
        System.out.println("newPassword: "+ newPassword);
        boolean result=this.userService.updatePassword(this.oldPassword, this.newPassword);
        if(result){
            params.put("success",true );

        }
        else{
            params.put("success",false);

        }
        return "ajax";
    }

    public String updateUserProfile(){
        this.params = new HashMap();
        boolean result = this.userService.updateUserProfile(this.userProfile);
        params.put("success", result);
        return "ajax";
    }

    public String updateUserPicture(){
        this.params = new HashMap();
        boolean result = this.userService.updateUserPicture(this.userPicture);
        params.put("success", result);
        //params.put("success",true);
        return "ajax";
    }
}
