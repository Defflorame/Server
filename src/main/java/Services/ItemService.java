package Services;

import DAO.ItemDAO;
import EntityDTO.ItemDTO;
import entity.Item;

import java.util.ArrayList;
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
    public List<Item> findAllEntities(){return itemDAO.findAll();}

    public List<ItemDTO> findAll() {
        List<ItemDTO> items = new ArrayList<>();
        for(var i : itemDAO.findAll())
        {
            items.add(new ItemDTO(i.getItemId(), i.getName(), i.getCount(), i.getPrice()));
        }
        return items;
    }
}
