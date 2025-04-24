package Services;

import DAO.ItemDAO;
import entity.Item;

import java.util.List;

public class ItemService implements Service<Item> {
    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    public Item findEntity(int id) {
        return itemDAO.findById(id);
    }

    @Override
    public void saveEntity(Item role) {
        itemDAO.save(role);
    }

    @Override
    public void deleteEntity(Item role) {

        itemDAO.delete(role);
    }

    @Override
    public void updateEntity(Item role) {
        itemDAO.update(role);
    }

    @Override
    public List<Item> findAllEntities() {
        return itemDAO.findAll();
    }
}
