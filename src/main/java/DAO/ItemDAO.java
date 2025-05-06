package DAO;

import HSF.SessionConfig;
import entity.Item;
import HSF.SessionConfig;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ItemDAO implements DAO<Item> {
    private final SessionFactory sessionFactory;

    public ItemDAO() {
        this.sessionFactory = SessionConfig.getInstance().getSessionFactory();
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
    public List<Item> findByIds(Set<Integer> ids) {
        try (Session session = sessionFactory.openSession()) {
            if (ids == null || ids.isEmpty()) return Collections.emptyList();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Item> query = cb.createQuery(Item.class);
            Root<Item> root = query.from(Item.class);

            // WHERE i.itemId IN :ids
            query.select(root).where(root.get("itemId").in(ids));

            return session.createQuery(query).getResultList();
        }
    }

}
