package dao.impl;

import com.mongodb.DB;
import dao.BaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

public class BaseDaoImpl implements BaseDao {
    protected SessionFactory sessionFactory;
    protected MongoTemplate mongoTemplate;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public DB getMongoDb() {
        return mongoTemplate.getDb();
    }

    @Override
    public boolean save(Object obj) {
        getSession().save(obj);
        return true;
    }

    @Override
    public boolean update(Object obj) {
        getSession().update(obj);
        return true;
    }

    @Override
    public boolean delete(Object obj) {
        getSession().delete(obj);
        return true;
    }
}
