package by.dk.training.items.services;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.dk.training.items.dataaccess.filters.TypeFilter;
import by.dk.training.items.datamodel.Package;
import by.dk.training.items.datamodel.Product;
import by.dk.training.items.datamodel.Recipient;
import by.dk.training.items.datamodel.StatusUser;
import by.dk.training.items.datamodel.Type;
import by.dk.training.items.datamodel.UserCredentials;
import by.dk.training.items.datamodel.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class AllNew {

	@Inject
	private UserProfileService userProfileService;
	@Inject
	private PackageService packageService;
	@Inject
	private RecipientService recipientService;
	@Inject
	private TypeService typeService;
	@Inject
	private ProductService productService;

	@Test
	public void testTypeCreate() {

		UserProfile user;
		UserCredentials userCredentials;
		for (int i = 0; i < 100; i++) {
			user = new UserProfile();
			userCredentials = new UserCredentials();

			user.setLogin("Login " + i);
			user.setPassword("123456");
			userCredentials.setEmail(i + "@gmail.com");
			userCredentials.setFirstName("Имя " + i);
			userCredentials.setLastName("Фамилия " + i);
			userProfileService.register(user, userCredentials);
		}
		UserCredentials userAdmin;
		userAdmin = userProfileService.getUserCredentials(1L);
		userAdmin.setStatus(StatusUser.ADMIN);
		userProfileService.update(userAdmin);
		UserCredentials userCommander;
		userCommander = userProfileService.getUserCredentials(2L);
		userCommander.setStatus(StatusUser.COMMANDER);
		userProfileService.update(userCommander);

		Recipient recipient;
		for (int i = 1; i <= 100; i++) {

			recipient = new Recipient();
			recipient.setIdUser(userProfileService.getUser((long) i));
			recipient.setName("Имя " + i);
			recipient.setCity("Город " + i);
			recipient.setAddress("Адрес " + i);
			recipientService.register(recipient);
		}

		TypeFilter filter = new TypeFilter();
		Type type;
		type = new Type();
		type.setIdUser(userProfileService.getUser((long) 1));
		type.setTypeName("Электроника и бытовая техника");
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Компьютерная техника");
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Телефоны и связь");
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Фото, видео");
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Мебель, интерьер, обиход");
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Одежда, обувь, аксессуары");
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Животные и растения");
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Книга, учебники, журналы");
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Продукты питания");
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Строительные материалы");
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Запрещенный для ввоза");
		typeService.register(type);
		type.setId(null);
		type.setTypeName("другие");
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Кухонная техника");
		filter.setTypeName("Электроника и бытовая техника");
		type.setParentType(typeService.find(filter).get(0));
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Ноутбуки");
		filter.setTypeName("Компьютерная техника");
		type.setParentType(typeService.find(filter).get(0));
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Мобильные телефоны");
		filter.setTypeName("Телефоны и связь");
		type.setParentType(typeService.find(filter).get(0));
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Фото-, видеокамеры, объективы");
		filter.setTypeName("Фото, видео");
		type.setParentType(typeService.find(filter).get(0));
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Мебель для гостиной");
		filter.setTypeName("Мебель, интерьер, обиход");
		type.setParentType(typeService.find(filter).get(0));
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Бижутерия, аксессуары");
		filter.setTypeName("Одежда, обувь, аксессуары");
		type.setParentType(typeService.find(filter).get(0));
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Растения");
		filter.setTypeName("Животные и растения");
		type.setParentType(typeService.find(filter).get(0));
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Чай, кофе, напитки");
		filter.setTypeName("Продукты питания");
		type.setParentType(typeService.find(filter).get(0));
		typeService.register(type);
		type.setId(null);
		type.setTypeName("Растворы");
		filter.setTypeName("Строительные материалы");
		type.setParentType(typeService.find(filter).get(0));
		typeService.register(type);
		type.setId(null);

		Product product = new Product();
		TypeFilter filterType = new TypeFilter();
		filterType.setFetchChildTypes(true);
		filterType.setFetchParentType(true);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Lenovo Z510");
		product.setPriceProduct(new BigDecimal("10000000"));
		product.setStatus(true);
		filterType.setTypeName("Ноутбуки");
		product.setTypes(typeService.find(filterType).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setId(null);
		product.setIdUser(userProfileService.getUser((long) 3));
		product.setNameProduct("Nokia 6600");
		product.setPriceProduct(new BigDecimal("3000000"));
		product.setStatus(true);
		filterType.setTypeName("Мобильные телефоны");
		product.setTypes(typeService.find(filterType).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setId(null);
		product.setIdUser(userProfileService.getUser((long) 4));
		product.setNameProduct("Gefest 3424");
		product.setPriceProduct(new BigDecimal("15000000"));
		product.setStatus(true);
		filterType.setTypeName("Кухонная техника");
		product.setTypes(typeService.find(filterType).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setId(null);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Samsung GT7000+");
		product.setPriceProduct(new BigDecimal("4000000"));
		product.setStatus(true);
		filterType.setTypeName("Фото-, видеокамеры, объективы");
		product.setTypes(typeService.find(filterType).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setId(null);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Командор-343");
		product.setPriceProduct(new BigDecimal("7000000"));
		product.setStatus(true);
		filterType.setTypeName("Мебель для гостиной");
		product.setTypes(typeService.find(filterType).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setId(null);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Спортивная сумка-SPORT 3000");
		product.setPriceProduct(new BigDecimal("1000000"));
		product.setStatus(true);
		filterType.setTypeName("Бижутерия, аксессуары");
		product.setTypes(typeService.find(filterType).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setId(null);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Кактус");
		product.setPriceProduct(new BigDecimal("500000"));
		product.setStatus(true);
		filterType.setTypeName("Растения");
		product.setTypes(typeService.find(filterType).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setId(null);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Nescafe");
		product.setPriceProduct(new BigDecimal("70000"));
		product.setStatus(true);
		filterType.setTypeName("Чай, кофе, напитки");
		product.setTypes(typeService.find(filterType).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setId(null);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Цемент М-500");
		product.setPriceProduct(new BigDecimal("50000"));
		product.setStatus(true);
		filterType.setTypeName("Растворы");
		product.setTypes(typeService.find(filterType).get(0));
		product.setWeight(50.0);
		productService.register(product);
		product.setId(null);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Бампер");
		product.setPriceProduct(new BigDecimal("10000000"));
		product.setStatus(true);
		filterType.setTypeName("другие");
		product.setTypes(typeService.find(filterType).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setId(null);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Какаин");
		product.setPriceProduct(new BigDecimal("10000000"));
		product.setStatus(true);
		filterType.setTypeName("Запрещенный для ввоза");
		product.setTypes(typeService.find(filterType).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setId(null);

		Package pack = new Package();
		BigDecimal fine = new BigDecimal("0");
		BigDecimal percentFine = new BigDecimal("20");
		long idPack = System.currentTimeMillis();
		for (int k = 1; k <= 10; k++) {
			int n = 1;
			int nn = 1;
			@SuppressWarnings("unused")
			int nnn = 1;
			for (int i = 1; i <= 100; i++) {
				pack.setId(idPack++);
				pack.setCountrySender("Country " + i);
				pack.setFine(fine);
				pack.setPercentFine(percentFine);
				if (i % 10 == 0) {
					n++;
					nn++;
				}
				pack.setIdRecipient(recipientService.getRecipient((long) n));
				pack.setIdUser(userProfileService.getUser((long) nn));
				pack.setPaid(false);
				pack.setPaymentDeadline("10");
				if (i % 100 == 0) {
					nnn++;
				}
//				pack.setProducts(productService.get((long) nnn));
				pack.setPrice(pack.getProducts().get(0).getPriceProduct());
				packageService.register(pack);
			}
		}
		List<Package> allPacks = packageService.getAll();
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.YEAR, 2013);
		d.setTime(cal.getTime().getTime());
		int count = 0;
		int m = 0;
		int day = 1;
		for (Package p : allPacks) {
			p.setDate(d);
			count++;
			if (count % 333 == 0) {
				cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
			}
			if (count % 30 == 0) {
				if (m == 12) {
					m = 0;
				}
				cal.set(Calendar.MONTH, m);
				m++;
			}
			if (day == 31) {
				day = 1;
			}
			cal.set(Calendar.DAY_OF_MONTH, day);
			d.setTime(cal.getTime().getTime());
			packageService.update(p);
		}
	}
}
