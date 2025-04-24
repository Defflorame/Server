package Services;

import DAO.UserDAO;
import entity.User;
import EntityDTO.UserDTO;

import java.util.List;

public class UserService implements Service<User> {

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

    public UserDTO login(User user)
    {
        User user1 = userDAO.login(user);
        if(user1 != null)
        {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(user1.getUserName());
            userDTO.setPassword(null);
            userDTO.setRole(user1.getRole().getRoleName());
            return userDTO;
        }
        else return null;
    }
}
