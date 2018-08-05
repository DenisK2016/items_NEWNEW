import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.hibernate.Session;

import by.dk.training.items.datamodel.Package;
import by.dk.training.items.datamodel.Product;
import by.dk.training.items.datamodel.Ranks;
import by.dk.training.items.datamodel.Recipient;
import by.dk.training.items.datamodel.StatusUser;
import by.dk.training.items.datamodel.Type;
import by.dk.training.items.datamodel.UserCredentials;
import by.dk.training.items.datamodel.UserProfile;
import persistence.HibernateUtil;

public class Main {

	public static void main(String[] args) {

		Session session = HibernateUtil.sessionFactory.openSession();
		try {
			session.beginTransaction();

			UserCredentials userCredentials = new UserCredentials();
			userCredentials.setCreated(new Date());
			userCredentials.setEmail("sample@mail.com");
			userCredentials.setFirstName("FName");
			userCredentials.setLastName("LName");
			userCredentials.setPost("Post");
			userCredentials.setRank(Ranks.ADVISER_FIRST);
			userCredentials.setStatus(StatusUser.ADMIN);
			UserProfile userProfile = new UserProfile();
			userProfile.setLogin("ADMIN");
			userProfile.setPassword("1234567");
			userCredentials.setUser(userProfile);
			session.save(userCredentials);
			userProfile.setUserCredentials(userCredentials);
			session.save(userProfile);

			Type type = new Type();
			type.setIdUser(userProfile);
			type.setTypeName("Type Name");
			session.save(type);
			Type childtype = new Type();
			childtype.setIdUser(userProfile);
			childtype.setTypeName("childtype Name");
			session.save(childtype);
			childtype.setParentType(type);
			type.setChildTypes(childtype);
			session.save(childtype);
			session.save(type);

			Recipient recipient = new Recipient();
			recipient.setName("Name");
			recipient.setCity("City");
			recipient.setAddress("address");
			recipient.setIdUser(userProfile);
			session.save(recipient);

			Product product = new Product();
			product.setIdUser(userProfile);
			product.setNameProduct("Product name");
			product.setPriceProduct(new BigDecimal(12345));
			product.setStatus(true);
			product.setTypes(childtype);
			product.setWeight(45.34);
			session.save(product);

			Package pack = new Package();
			pack.setDescription("Desc");
			pack.setCountrySender("CS");
			pack.setDate(new Date());
			pack.setPaid(false);
			pack.setPaymentDeadline("20");
			pack.setPenalty(new BigDecimal("0"));
			pack.setPercentPenalty(new BigDecimal("0"));
			pack.setPrice(new BigDecimal("100"));
			pack.setProducts(new ArrayList<Product>(Arrays.asList(product)));
			pack.setRecipient(recipient);
			pack.setIdUser(userProfile);
			session.save(pack);

			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			HibernateUtil.shutdown();

		}

	}

}