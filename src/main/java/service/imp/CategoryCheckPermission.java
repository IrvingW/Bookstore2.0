package service.imp;

import java.security.Permission;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CategoryCheckPermission extends Permission {
    private String action;

    public CategoryCheckPermission(String target, String action){
        super(target);
        this.action = action;
        System.out.println(target+action);
    }

    @Override
    public String getActions() {
        return action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),action);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!getClass().equals(obj.getClass())) return false;
        CategoryCheckPermission b = (CategoryCheckPermission) obj;
        if (!Objects.equals(action, b.action)) return false;
        if ("child".equals(action)) return Objects.equals(getName(), b.getName());
        else if ("adult".equals(action)) return adultSet().equals(b.adultSet());
        else return false;
    }

    @Override
    public boolean implies(Permission other) {
        if (!(other instanceof CategoryCheckPermission)) return false;
        CategoryCheckPermission b = (CategoryCheckPermission) other;

        if (action.equals("child"))
        {
            return b.action.equals("child") && getName().indexOf(b.getName()) >= 0;
        }
        else if (action.equals("adult"))
        {
            if (b.action.equals("adult")) //return b.adultSet().containsAll(adultSet());
                return true;
            else if (b.action.equals("child"))
            {
                for (String adultPage : adultSet())
                    if (b.getName().indexOf(adultPage) >= 0) return false;
                return true;
            }
            else return false;
        }
        else return false;
    }

    public Set<String> adultSet(){
        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(getName().split(",")));
        return set;
    }
}


