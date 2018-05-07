package common.filter;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.multipart.MultiPartRequest;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.opensymphony.xwork2.LocaleProvider;

import common.utils.XssShieldUtil;

public class MyMultiPartRequestWrapper extends MultiPartRequestWrapper{
    public MyMultiPartRequestWrapper(MultiPartRequest multiPartRequest, HttpServletRequest request,
                                     String saveDir, LocaleProvider provider) {
        super(multiPartRequest, request, saveDir, provider);
    }

    @Override
    public String getParameter(String name) {
        name = XssShieldUtil.stripXss(name);
        // 返回值之前 先进行过滤
        return XssShieldUtil.stripXss(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {
        name = XssShieldUtil.stripXss(name);
        // 返回值之前 先进行过滤
        String[] values = super.getParameterValues(name);
        if(values != null){
            for (int i = 0; i < values.length; i++) {
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
