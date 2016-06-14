package by.dk.training.items.services;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.dk.training.items.datamodel.Product;
import by.dk.training.items.datamodel.Recipient;
import by.dk.training.items.datamodel.UserCredentials;
import by.dk.training.items.datamodel.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class NewProductTest {

	@Inject
	private ProductService productService;
	@Inject
	private RecipientService recipientService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private TypeService typeService;

	@Test
	public void testProduct() {

		UserProfile user = new UserProfile();
		UserCredentials userCred = new UserCredentials();
		user.setLogin("Userrr");
		user.setPassword("123456");
		userCred.setEmail("uuuuuu@mail.ru");
		userCred.setFirstName("имяяяя");
		userCred.setLastName("фамилия");
		userProfileService.register(user, userCred);

		Product product;
		BigDecimal bd = new BigDecimal("100000");
		for (int i = 0; i < 1000000; i++) {
			product = new Product();
			product.setIdUser(user);
			product.setNameProduct("ИмяПРодукта " + i);
			product.setPriceProduct(bd.add(new BigDecimal(i + "")));
			product.setStatus(true);
			product.setWeight(7.0);
			product.setTypes(typeService.get(1L));
			productService.register(product);
			System.out.println("i=" + i);
		}

		Recipient recipient;

		for (int k = 0; k < 1000000; k++) {
			recipient = new Recipient();
			recipient.setAddress("Лермантово 12");
			recipient.setCity("Минск");
			recipient.setIdUser(user);
			recipient.setName("ФИО " + k);
			recipientService.register(recipient);
			System.out.println("k=" + k);
		}

	}
}
