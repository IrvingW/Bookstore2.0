package common.filter;

import common.utils.XssShieldUtil;
import org.apache.struts2.dispatcher.StrutsRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class MyStrutsRequestWrapper extends StrutsRequestWrapper {
    public MyStrutsRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public MyStrutsRequestWrapper(HttpServletRequest request, boolean bool) {
        super(request, bool);
    }

    @Override
    public String getParameter(String name) {
        name = XssShieldUtil.stripXss(name);
        return XssShieldUtil.stripXss(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {
        name = XssShieldUtil.stripXss(name);

        String[] values = super.getParameterValues(name);
        if(values != null) {
            for(int i = 0;i < values.length;i++) {
                values[i] = XssShieldUtil.stripXss(values[i]);
            }
        }
        return values;
     }

     @Override
     public Enumeration<String> getParameterNames() {
        return super.getParameterNames();
     }

     @Override
     public Map getParameterMap() {
         Map<String, String[]> paramMap = super.getParameterMap();
         Set<String> keySet = paramMap.keySet();
         for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
             String key = (String) iterator.next();
             String[] str = paramMap.get(key);
             if (str != null) {
                 for (int i = 0; i < str.length; i++) {
                     str[i] = XssShieldUtil.stripXss(str[i]);
                 }
             }
         }
         return paramMap;
     }
}
