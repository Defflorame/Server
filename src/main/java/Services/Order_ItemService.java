package Services;

import DAO.Order_ItemDAO;
import entity.Order_Item;

import java.util.List;

public class Order_ItemService implements Service<Order_Item> {
    private final Order_ItemDAO orderItemDAO = new Order_ItemDAO();

    @Override
    public Order_Item findEntity(int id) {
        return orderItemDAO.findById(id);
        
    }

    @Override
    public void saveEntity(Order_Item role) {
        orderItemDAO.save(role);
    }

    @Override
    public void deleteEntity(Order_Item role) {

        orderItemDAO.delete(role);
    }

    @Override
    public void updateEntity(Order_Item role) {
        orderItemDAO.update(role);
    }

    @Override
    public List<Order_Item> findAllEntities() {
        return orderItemDAO.findAll();
    }
}
