package Services;

import DAO.BuyerDAO;
import entity.Buyer;

import java.util.List;

public class BuyerService implements Service<Buyer> {

    private final BuyerDAO buyerDAO = new BuyerDAO();

    @Override
    public Buyer findEntity(int id) {
        return buyerDAO.findById(id);
    }

    @Override
    public void saveEntity(Buyer role) {
        buyerDAO.save(role);
    }

    @Override
    public void deleteEntity(Buyer role) {

        buyerDAO.delete(role);
    }

    @Override
    public void updateEntity(Buyer role) {
        buyerDAO.update(role);
    }

    @Override
    public List<Buyer> findAllEntities() {
        return buyerDAO.findAll();
    }
}
