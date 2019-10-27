package lt.ocirama.leiSystem.Utils;

import lt.ocirama.leiSystem.Models.OrderEntity;
import lt.ocirama.leiSystem.Models.SampleEntity;
import lt.ocirama.leiSystem.Models.TrayEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static void init(String database, String user, String password) {
        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        settings.put(Environment.URL, "jdbc:mysql://localhost:3306/" + database + "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        settings.put(Environment.USER, user);
        settings.put(Environment.PASS, password);
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "update");
        configuration.setProperties(settings);
        configuration.addAnnotatedClass(OrderEntity.class);
        configuration.addAnnotatedClass(SampleEntity.class);
        configuration.addAnnotatedClass(TrayEntity.class);
        sessionFactory = configuration.buildSessionFactory(
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build()
        );
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            throw new RuntimeException("Session factory not initialized");
        }
        return sessionFactory;
    }
}

