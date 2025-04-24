package DAO;

import HSF.SessionConfig;
import entity.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

public class UserDAO implements DAO<User> {
    private final SessionFactory sessionFactory;

    public UserDAO() {
        this.sessionFactory = SessionConfig.getInstance().getSessionFactory();
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
    public List<User> findAll()
    {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root);
            Query<User> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }


    // Для логина
    public User login(User user)
    {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM User WHERE userName = :username AND password = :password";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", user.getUserName());
            query.setParameter("password", user.getPassword());
            return query.uniqueResult();
        }
    }
}
