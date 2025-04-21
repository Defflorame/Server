package DAO;

import Entity.Role;
import HSF.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class RoleDAO implements DAO<Role> {
    private final SessionFactory sessionFactory;

    public RoleDAO() {
        this.sessionFactory = HibernateSessionFactory.getInstance().getSessionFactory();
    }

    @Override
    public void save(Role role) {
        executeTransaction(sessionFactory, Session::save, role);
    }

    @Override
    public void update(Role role) {
        executeTransaction(sessionFactory, Session::update, role);
    }

    @Override
    public void delete(Role role) {
        executeTransaction(sessionFactory, Session::delete, role);
    }

    @Override
    public Role findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Role.class, id);
        }
    }

    @Override
    public List<Role> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Role> query = session.createQuery("FROM Role", Role.class);
            return query.list();
        }
    }
}
