package DAO;

import HSF.SessionConfig;
import entity.Order;
import HSF.SessionConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDAO implements DAO<Order> {
    private final SessionFactory sessionFactory;

    public OrderDAO() {
        this.sessionFactory = SessionConfig.getInstance().getSessionFactory();
    }

    @Override
    public void save(Order order) {
        executeTransaction(sessionFactory, Session::save, order);
    }

    @Override
    public void update(Order order) {
        executeTransaction(sessionFactory, Session::update, order);
    }

    @Override
    public void delete(Order order) {
        executeTransaction(sessionFactory, Session::delete, order);
    }

    @Override
    public Order findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Order.class, id);
        }
    }

    @Override
    public List<Order> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Order> query = session.createQuery("FROM Order", Order.class);
            return query.list();
        }
    }
}
