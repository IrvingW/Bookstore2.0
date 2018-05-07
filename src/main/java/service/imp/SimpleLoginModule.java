package service.imp;

import common.constants.UserRole;
import common.utils.PasswordUtil;
import common.utils.SpringTool;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;
import service.LoginModule;
import service.SimplePrincipal;

import java.io.*;
import java.nio.file.Paths;
import java.security.*;
import java.util.*;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;
import javax.servlet.ServletContext;


public class SimpleLoginModule implements LoginModule {
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> options;
    private UserDao userDao;

   public UserDao getUserDao() {
      return userDao;
   }

   public void setUserDao(UserDao userDao) {
      this.userDao = userDao;
   }

   public void setSubject(Subject subject) {
      this.subject = subject;
   }

   public void setOptions(Map<String, ?> options) {
      this.options = options;
   }

   public void setCallbackHandler(CallbackHandler callbackHandler) {
      this.callbackHandler = callbackHandler;
   }

   public Subject getSubject() {
      return subject;
   }

   public Map<String, ?> getOptions() {
      return options;
   }

   public CallbackHandler getCallbackHandler() {
      return callbackHandler;
   }

   @Override
   public void initialize(Subject subject, CallbackHandler callbackHandler,
                          Map<String, ?> sharedState, Map<String, ?> options){
      this.subject = subject;
      this.callbackHandler = callbackHandler;
      this.options = options;
   }

   @Override
   public boolean login() throws LoginException
   {
      if (callbackHandler == null) throw new LoginException("no handler");

      NameCallback nameCall = new NameCallback("username: ");
      PasswordCallback passCall = new PasswordCallback("password: ", false);
      try
      {
         callbackHandler.handle(new Callback[] { nameCall, passCall });
      }
      catch (UnsupportedCallbackException e)
      {
         LoginException e2 = new LoginException("Unsupported callback");
         e2.initCause(e);
         throw e2;
      }
      catch (IOException e)
      {
         LoginException e2 = new LoginException("I/O exception in callback");
         e2.initCause(e);
         throw e2;
      }

      try
      {
         return checkLogin(nameCall.getName(), passCall.getPassword());
      }
      catch (IOException ex)
      {
         LoginException ex2 = new LoginException();
         ex2.initCause(ex);
         throw ex2;
      }
   }


   private boolean checkLogin(String username, char[] password) throws LoginException, IOException
   {
      System.out.println(username);
      String pwd = "";
      for(char c : password){
         pwd += c;
      }
      System.out.println(pwd);
      /*
      try (Scanner in = new Scanner(Paths.get("" + options.get("pwfile"))))
      {
         while (in.hasNextLine())
         {
            String[] inputs = in.nextLine().split("\\|");
            if (inputs[0].equals(username) && Arrays.equals(inputs[1].toCharArray(), password))
            {
               String role = inputs[2];
               Set<Principal> principals = subject.getPrincipals();
               principals.add(new SimplePrincipal(role));
               return true;
            }
         }
         return false;
      }
      */

      User user = this.userDao.getUserByEmail(username);
      if(user == null) return false;
      if(PasswordUtil.checkPassword(pwd,user.getPassword())){
         String role = "";
         UserRole userRole = user.getRole();
         if(userRole == UserRole.ADMIN){
            role = "admin";
         }
         else{
            role = "guest";
         }
         Set<Principal> principals = subject.getPrincipals();
         principals.add(new SimplePrincipal(role));
         System.out.println("role="+role);
         return true;
      }

      return false;
   }

   @Override
   public boolean logout()
   {
      return true;
   }

   @Override
   public boolean abort()
   {
      return true;
   }

   @Override
   public boolean commit()
   {
      return true;
   }
}
