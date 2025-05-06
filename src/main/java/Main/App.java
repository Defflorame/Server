package Main;

import HSF.SessionConfig;
import Services.UserService;
import entity.User;

import network.Server;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;


public class App {
    public static void main(String[] args)
    {

        try {
            Server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
