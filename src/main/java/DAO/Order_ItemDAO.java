package DAO;

import HSF.SessionConfig;
import entity.Order_Item;
import HSF.SessionConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class Order_ItemDAO implements DAO<Order_Item> {
    private final SessionFactory sessionFactory;

    public Order_ItemDAO() {
        this.sessionFactory = SessionConfig.getInstance().getSessionFactory();
    }

    @Override
    public void save(Order_Item orderItem) {
        executeTransaction(sessionFactory, Session::save, orderItem);
    }

    @Override
    public void update(Order_Item buyer) {
        executeTransaction(sessionFactory, Session::update, buyer);
    }

    @Override
    public void delete(Order_Item buyer) {
        executeTransaction(sessionFactory, Session::delete, buyer);
    }

    @Override
    public Order_Item findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Order_Item.class, id);
        }
    }

    @Override
    public List<Order_Item> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Order_Item> query = session.createQuery("FROM Order_Item", Order_Item.class);
            return query.list();
        }
    }
}
