package DAO;

import Entity.Item;
import HSF.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ItemDAO implements DAO<Item> {
    private final SessionFactory sessionFactory;

    public ItemDAO() {
        this.sessionFactory = HibernateSessionFactory.getInstance().getSessionFactory();
    }

    @Override
    public void save(Item item) {
        executeTransaction(sessionFactory, Session::save, item);
    }

    @Override
    public void update(Item item) {
        executeTransaction(sessionFactory, Session::update, item);
    }

    @Override
    public void delete(Item item) {
        executeTransaction(sessionFactory, Session::delete, item);
    }

    @Override
    public Item findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Item.class, id);
        }
    }

    @Override
    public List<Item> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Item> query = session.createQuery("FROM Item", Item.class);
            return query.list();
        }
    }
}
