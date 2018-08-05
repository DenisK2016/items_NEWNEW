package persistence;

import org.hibernate.SessionFactory;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import by.dk.training.items.datamodel.util.CustomImprovedNamingStrategy;

@SuppressWarnings("deprecation")
public class HibernateUtil {
	public static final SessionFactory sessionFactory;

	static {
		sessionFactory = new Configuration().configure().setNamingStrategy(new CustomImprovedNamingStrategy())
				.buildSessionFactory();
	}

	public static void shutdown() {
		sessionFactory.close();
	}
}
