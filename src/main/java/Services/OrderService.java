package Services;

import DAO.DAO;
import DAO.OrderDAO;
import DAO.UserDAO;
import DAO.ItemDAO;
import EntityDTO.ItemDTO;
import entity.Item;
import entity.Order;
import entity.Order_Item;
import entity.User;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderService implements Service<Order> {
    private final OrderDAO orderDAO = new OrderDAO();
    private final UserDAO userDAO = new UserDAO();
    private final ItemDAO itemDAO = new ItemDAO();

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



    // synchronized???
    public int createOrder(Map<Integer, ArrayList<ItemDTO>> cart) {
        Map.Entry<Integer, ArrayList<ItemDTO>> entry = cart.entrySet().iterator().next();
        int userId = entry.getKey();
        List<ItemDTO> items = entry.getValue();

        User user = userDAO.findById(userId);
        if (user == null) {
            System.out.println("Пользователь не найден!");
            return -1;
        }

        Set<Integer> itemIds = items.stream()
                .map(ItemDTO::getItemId)
                .collect(Collectors.toSet());

        List<Item> dbItems = itemDAO.findByIds(itemIds);
        if (dbItems.isEmpty()) {
            System.out.println("Нет доступных товаров для заказа.");
            return -1;
        }

        Map<Integer, Item> dbItemMap = dbItems.stream()
                .collect(Collectors.toMap(Item::getItemId, Function.identity()));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        Set<Order_Item> orderItems = new HashSet<>();
        int check = 0;
        for (ItemDTO dto : items) {
            Item dbItem = dbItemMap.get(dto.getItemId());
            if (dbItem != null && dto.getCount() <= dbItem.getCount()) {
                // уменьшение остатка
                dbItem.setCount(dbItem.getCount() - dto.getCount());
                itemDAO.update(dbItem);

                Order_Item orderItem = new Order_Item();
                orderItem.setItem(dbItem);
                orderItem.setItemCount(dto.getCount());
                orderItem.setOrder(order);

                orderItems.add(orderItem);
                check++;

            }
            else {
                System.out.println("Недостаточно товара!");
            }
        }

        if (orderItems.isEmpty()) {
            System.out.println("Невозможно создать заказ — нет подходящих товаров.");
            return -1;
        }

        order.setOrderItems(orderItems);
        orderDAO.save(order);
        if(check == items.size()) return 1;
        else return 0;
    }

}
