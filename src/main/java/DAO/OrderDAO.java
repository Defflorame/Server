package DAO;

import EntityDTO.OrderItemInfoDTO;
import HSF.SessionConfig;
import entity.Item;
import entity.Order;
import HSF.SessionConfig;
import entity.Order_Item;
import entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
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

    public List<OrderItemInfoDTO> findByUserId(int userId) {
        try (Session session = sessionFactory.openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<OrderItemInfoDTO> query = cb.createQuery(OrderItemInfoDTO.class);

            Root<Order> orderRoot = query.from(Order.class);
            Join<Order, Order_Item> orderItemJoin = orderRoot.join("orderItems");
            Join<Order_Item, Item> itemJoin = orderItemJoin.join("item");

            query.select(cb.construct(
                    OrderItemInfoDTO.class,
                    orderRoot.get("orderDate"),
                    itemJoin.get("name"),
                    itemJoin.get("price"),
                    orderItemJoin.get("itemCount")
            ));

            query.where(cb.equal(orderRoot.get("user").get("userId"), userId));

            return session.createQuery(query).getResultList();
        }
    }




}
