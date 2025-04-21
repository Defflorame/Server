package DAO;

import Entity.User;
import HSF.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO implements DAO<User> {
    private final SessionFactory sessionFactory;

    public UserDAO() {
        this.sessionFactory = HibernateSessionFactory.getInstance().getSessionFactory();
    }

    @Override
    public void save(User user) {
        executeTransaction(sessionFactory, Session::save, user);
    }

    @Override
    public void update(User user) {
        executeTransaction(sessionFactory, Session::update, user);
    }

    @Override
    public void delete(User user) {
        executeTransaction(sessionFactory, Session::delete, user);
    }

    @Override
    public User findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        }
    }
}
