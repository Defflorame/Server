package DAO;

import Entity.Buyer;
import HSF.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class BuyerDAO implements DAO<Buyer>{
    private final SessionFactory sessionFactory;

    public BuyerDAO() {
        this.sessionFactory = HibernateSessionFactory.getInstance().getSessionFactory();
    }

    @Override
    public void save(Buyer buyer) {
        executeTransaction(sessionFactory, Session::save, buyer);
    }

    @Override
    public void update(Buyer buyer) {
        executeTransaction(sessionFactory, Session::update, buyer);
    }

    @Override
    public void delete(Buyer buyer) {
        executeTransaction(sessionFactory, Session::delete, buyer);
    }

    @Override
    public Buyer findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Buyer.class, id);
        }
    }

    @Override
    public List<Buyer> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Buyer> query = session.createQuery("FROM Buyer", Buyer.class);
            return query.list();
        }
    }
}
