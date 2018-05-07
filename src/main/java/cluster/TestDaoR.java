package cluster;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class TestDaoR {
    protected SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public testUser getUser(int userID) {
        String hql = "from testUser u where u.userID = :userID";
        Query query = getSession().createQuery(hql).setParameter("userID", userID);
        List<testUser> users = query.list();
        testUser user = users.size() == 1 ? users.get(0) : null;
        return user;
    }
}
