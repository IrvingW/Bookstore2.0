package dao.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import common.constants.UserRole;
import dao.UserDao;
import model.User;
import model.UserProfile;
import org.bson.types.ObjectId;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    public UserDaoImpl(){}

    @Override
    public User getUserById(int userID) {
        String hql = "from User u where u.userID = :userID";
        Query query = getSession().createQuery(hql).setParameter("userID", userID);
        List<User> users = query.list();
        User user = users.size() == 1 ? users.get(0) : null;
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        String hql = "from User where email = :email";
        Query query = getSession().createQuery(hql).setParameter("email", email);
        List result = query.list();
        if(result.size() == 1) {
            return (User)result.get(0);
        }
        return null;
    }

    @Override
    public UserProfile getUserProfile(int userID) {
        User user = this.getUserById(userID);
        DBCollection collection = getMongoDb().getCollection("user_profile");
        DBObject query=new BasicDBObject("_id", new ObjectId(user.getProfileID()));
        DBObject obj = collection.findOne(query);
        Map userProfileInMongo = (obj!=null) ? (Map)obj : null;

        UserProfile userProfile = new UserProfile();
        userProfile.setUserID(user.getUserID());
        userProfile.setEmail(user.getEmail());
        userProfile.setRole(user.getRole());
        userProfile.setUserRole(user.getRole().toString());
        userProfile.setImageID(user.getImageID());

        if(userProfileInMongo != null) {
            userProfile.setNickName((String)userProfileInMongo.get("nickname"));
            userProfile.setGender((String)userProfileInMongo.get("gender"));
            userProfile.setMobile((String)userProfileInMongo.get("mobile"));
            userProfile.setAge((String)userProfileInMongo.get("age"));
        }
        else {
            userProfile.setNickName("");
            userProfile.setGender("");
            userProfile.setMobile("");
            userProfile.setAge("");
        }

        return userProfile;

    }

    @Override
    public boolean saveUserProfile(UserProfile newUserProfile) {
        DBCollection collection = getMongoDb().getCollection("user_profile");
        Map userProfileInMongo;
        userProfileInMongo = new HashMap();

        //mongo
        userProfileInMongo.put("nickname", newUserProfile.getNickName());
        userProfileInMongo.put("gender", newUserProfile.getGender());
        userProfileInMongo.put("mobile", newUserProfile.getMobile());
        userProfileInMongo.put("age", newUserProfile.getAge());

        String profileID;    // mongodb部分的id
        BasicDBObject document = new BasicDBObject(userProfileInMongo);
        collection.insert(document);
        profileID = ((ObjectId)document.get("_id")).toString();

        //mysql
        User newUser = new User();
        newUser.setPassword(newUserProfile.getPassword());   // !!!!!
        newUser.setEmail(newUserProfile.getEmail());
        newUser.setRole(newUserProfile.getRole());
        newUser.setProfileID(profileID);
        newUser.setImageID("");

        this.save(newUser);
        return true;
    }

    @Override
    public String saveUserProfileInMongo(Map userProfile) {
        // 此userprofile只是mongo中的属性
        DBCollection collection = getMongoDb().getCollection("user_profile");
        BasicDBObject document = new BasicDBObject(userProfile);
        collection.insert(document);
        return ((ObjectId)document.get("_id")).toString();
    }

    @Override
    public boolean deleteUser(User user) {
        // 需要删除mongodb中的user_profile、image，再删除mysql中的信息
        if(user == null) {
            return false;
        }

        DBCollection profileCollection = getMongoDb().getCollection("user_profile");
        if(user.getProfileID() != null && !user.getProfileID().equals("")) {
            DBObject profileQuery=new BasicDBObject("_id", new ObjectId(user.getProfileID()));
            profileCollection.remove(profileQuery);
        }

        DB db = this.getMongoDb();
        GridFS gridFS = new GridFS(db);
        if(user.getImageID() != null && !user.getImageID().equals("")) {
            DBObject query=new BasicDBObject("_id", new ObjectId(user.getImageID()));
            GridFSDBFile gridFSDBFile = gridFS.findOne(query);
            if(gridFSDBFile != null) {
                gridFS.remove(gridFSDBFile);
                return true;
            }
        }

        this.delete(user);

        return true;
    }

    @Override
    public boolean updateUserProfile(UserProfile newUserProfile) {
        DBCollection collection = getMongoDb().getCollection("user_profile");
        Integer userID = newUserProfile.getUserID();
        User oldUser = userID==null? null:this.getUserById(userID);
        Map userProfileInMongo;

        DBObject query=new BasicDBObject("_id", new ObjectId(oldUser.getProfileID()));
        DBObject obj = collection.findOne(query);
        userProfileInMongo = (obj!=null) ? (Map)obj : new HashMap();

        //mongo
        userProfileInMongo.put("nickname", newUserProfile.getNickName());
        userProfileInMongo.put("gender", newUserProfile.getGender());
        userProfileInMongo.put("mobile", newUserProfile.getMobile());
        userProfileInMongo.put("age",newUserProfile.getAge());

        //mysql
        String profileID;    // mongodb部分的id
        BasicDBObject document = new BasicDBObject(userProfileInMongo);
        collection.update(query, document);
        profileID = oldUser.getProfileID();

        // 用户在mysql中的属性
        this.update(oldUser);
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        String hql = "from User where role = :role";
        Query query = getSession().createQuery(hql);
        query.setParameter("role", UserRole.COMMON);
        List<User> users = query.list();
        return users;
    }
}
