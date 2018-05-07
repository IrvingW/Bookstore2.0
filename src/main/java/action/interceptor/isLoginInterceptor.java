package action.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import java.util.Map;

public class isLoginInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception{
        Map session = actionInvocation.getInvocationContext().getSession();
        if (session.containsKey("userInfo"))
            return actionInvocation.invoke();
        return Action.LOGIN;
    }
}
