package Main;

import DAO.OrderDAO;
import HSF.SessionConfig;
import Services.OrderService;
import Services.UserService;
import com.google.gson.Gson;
import entity.User;

import network.Server;
import java.io.IOException;
import java.util.List;


public class App {
    public static void main(String[] args)
    {
        OrderService serv = new OrderService();
        Gson gson = null;

        System.out.println(serv.findEntityByUserId(2));
        try {
            Server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
