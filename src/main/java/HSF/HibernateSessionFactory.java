package HSF;

import Entity.*;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

//Session session = HSF.HibernateSessionFactory.getInstance().getSessionFactory().openSession();
@Getter
public class HibernateSessionFactory {

    private static volatile HibernateSessionFactory instance;
    private final SessionFactory sessionFactory;

    private HibernateSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Role.class);
            configuration.addAnnotatedClass(Order.class);
            configuration.addAnnotatedClass(Item.class);
            configuration.addAnnotatedClass(Buyer.class);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());

            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Ошибка инициализации Hibernate: " + e);
        }
    }

    public static HibernateSessionFactory getInstance() {
        if (instance == null) {
            synchronized (HibernateSessionFactory.class) {
                if (instance == null) {
                    instance = new HibernateSessionFactory();
                }
            }
        }
        return instance;
    }

}
