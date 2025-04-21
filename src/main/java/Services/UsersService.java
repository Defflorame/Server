package Services;

import DAO.UserDAO;
import Entity.User;

import java.util.List;

public class UsersService implements Service<User> {

    private final UserDAO userDAO = new UserDAO();

    @Override
    public User findEntity(int id) {
        return userDAO.findById(id);
    }

    @Override
    public void saveEntity(User user) {
        userDAO.save(user);
    }

    @Override
    public void deleteEntity(User user) {

        userDAO.delete(user);
    }

    @Override
    public void updateEntity(User user) {
        userDAO.update(user);
    }

    @Override
    public List<User> findAllEntities() {
        return userDAO.findAll();
    }


}
