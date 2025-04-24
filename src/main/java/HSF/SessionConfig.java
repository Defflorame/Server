package HSF;

import entity.*;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//Session session = HSF.SessionConfig.getInstance().getSessionFactory().openSession();
@Getter
public class SessionConfig {
    private static SessionConfig sessionConfig;

    private final SessionFactory sessionFactory;

    private SessionConfig() {
        sessionFactory = new Configuration().
                addAnnotatedClass(Role.class).
                addAnnotatedClass(User.class).
                addAnnotatedClass(Order.class).
                addAnnotatedClass(Item.class).
                addAnnotatedClass(Order_Item.class).
                addAnnotatedClass(Buyer.class).
                buildSessionFactory();
    }

    synchronized public static SessionConfig getInstance() {
        if (sessionConfig == null) {
            sessionConfig = new SessionConfig();
        }

        return sessionConfig;
    }
}


