package Services;

import DAO.OrderDAO;
import entity.Order;

import java.util.List;

public class OrderService implements Service<Order> {
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    public Order findEntity(int id) {
        return orderDAO.findById(id);
    }

    @Override
    public void saveEntity(Order role) {
        orderDAO.save(role);
    }

    @Override
    public void deleteEntity(Order role) {

        orderDAO.delete(role);
    }

    @Override
    public void updateEntity(Order role) {
        orderDAO.update(role);
    }

    @Override
    public List<Order> findAllEntities() {
        return orderDAO.findAll();
    }
}
