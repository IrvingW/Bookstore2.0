package service;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.*;
import java.util.Scanner;

/**
 * This action looks up a system property.
 */
public class SysPropAction implements PrivilegedAction<String>
{
   private String propertyName;

   /**
    * Constructs an action for looking up a given property.
    * @param propertyName the property name (such as "user.home")
    */
   public SysPropAction(String propertyName)
   {
      this.propertyName = propertyName;
   }

   public String run()
   {
       //System.out.println(System.getProperty(propertyName));
      //return System.getProperty(propertyName);

       String result = "";
      try(Scanner in = new Scanner(Paths.get("/Users/lvjiawei/article.txt"))){
          while(in.hasNextLine()){
              result += in.nextLine();
          }
      } catch (IOException e){
          e.printStackTrace();
      }
      System.out.println(result);
      return result;

   }
}
