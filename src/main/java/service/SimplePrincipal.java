package service;

import java.security.*;
import java.util.*;

/**
 * A principal with a named value (such as "role=HR" or "username=harry").
 */
public class SimplePrincipal implements Principal
{
   //private String descr;
   private String value;

   public SimplePrincipal(String value)
   {
      //this.descr = descr;
      this.value = value;
   }

   /**
    * Returns the role name of this principal.
    * @return the role name
    */
   public String getName()
   {
      return this.value;
   }

   public boolean equals(Object otherObject)
   {
      if (this == otherObject) return true;
      if (otherObject == null) return false;
      if (getClass() != otherObject.getClass()) return false;
      SimplePrincipal other = (SimplePrincipal) otherObject;
      return Objects.equals(getName(), other.getName());
   }

   public int hashCode()
   {
      return Objects.hashCode(getName());
   }
}
