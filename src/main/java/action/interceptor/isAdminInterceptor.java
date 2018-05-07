package action.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import common.constants.UserRole;
import model.User;

import java.util.Map;

public class isAdminInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        Map session=actionInvocation.getInvocationContext().getSession();
        if(session.containsKey("userInfo"))
        {
            User userInfo =(User) session.get("userInfo");
            if(userInfo.getRole()== UserRole.ADMIN){
                return actionInvocation.invoke();
            }
            else
            {
                return "403";
            }
        }
        return Action.LOGIN;
    }
}
