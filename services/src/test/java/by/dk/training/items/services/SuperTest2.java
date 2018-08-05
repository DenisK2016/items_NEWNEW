package by.dk.training.items.services;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import by.dk.training.items.datamodel.Type;
import by.dk.training.items.datamodel.UserCredentials;
import by.dk.training.items.datamodel.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class SuperTest2 {

	@Inject
	private UserProfileService userProfile;
	@Inject
	private TypeService typeService;
	@Inject
	private RecipientService recipientService;
	@Inject
	private ProductService productService;
	@Inject
	private PackageService packageService;

	@Test
	public void superTestMethod() {

		UserProfile user0 = new UserProfile();
		UserCredentials userCredentials0 = new UserCredentials();
		user0.setLogin("Admin");
		user0.setPassword("123456");
		userCredentials0.setEmail("admin@gmail.com");
		userCredentials0.setFirstName("adminFirstName");
		userCredentials0.setLastName("adminLastName");
		userProfile.registerUser(user0, userCredentials0);
		UserProfile user1 = new UserProfile();
		UserCredentials userCredentials1 = new UserCredentials();
		user1.setLogin("Login1");
		user1.setPassword("123456");
		userCredentials1.setEmail("user1@mail.ru");
		userCredentials1.setFirstName("user1FirstName");
		userCredentials1.setLastName("user1LastName");
		userProfile.registerUser(user1, userCredentials1);
		UserProfile user2 = new UserProfile();
		UserCredentials userCredentials2 = new UserCredentials();
		user2.setLogin("Login2");
		user2.setPassword("123456");
		userCredentials2.setEmail("user21@mail.ru");
		userCredentials2.setFirstName("user2FirstName");
		userCredentials2.setLastName("user2LastName");
		userProfile.registerUser(user2, userCredentials2);
		UserProfile user3 = new UserProfile();
		UserCredentials userCredentials3 = new UserCredentials();
		user3.setLogin("Login3");
		user3.setPassword("123456");
		userCredentials3.setEmail("user3@mail.ru");
		userCredentials3.setFirstName("user3FirstName");
		userCredentials3.setLastName("user3LastName");
		userProfile.registerUser(user3, userCredentials3);
		UserProfile user4 = new UserProfile();
		UserCredentials userCredentials4 = new UserCredentials();
		user4.setLogin("Login4");
		user4.setPassword("123456");
		userCredentials4.setEmail("user4@mail.ru");
		userCredentials4.setFirstName("user4FirstName");
		userCredentials4.setLastName("user4LastName");
		userProfile.registerUser(user4, userCredentials4);
		UserProfile user5 = new UserProfile();
		UserCredentials userCredentials5 = new UserCredentials();
		user5.setLogin("Login5");
		user5.setPassword("123456");
		userCredentials5.setEmail("user5@mail.ru");
		userCredentials5.setFirstName("user5FirstName");
		userCredentials5.setLastName("user5LastName");
		userProfile.registerUser(user5, userCredentials5);
		UserProfile user6 = new UserProfile();
		UserCredentials userCredentials6 = new UserCredentials();
		user6.setLogin("Login6");
		user6.setPassword("123456");
		userCredentials6.setEmail("user6@mail.ru");
		userCredentials6.setFirstName("user6FirstName");
		userCredentials6.setLastName("user6LastName");
		userProfile.registerUser(user6, userCredentials6);
		UserProfile user7 = new UserProfile();
		UserCredentials userCredentials7 = new UserCredentials();
		user7.setLogin("Login7");
		user7.setPassword("123456");
		userCredentials7.setEmail("user7@mail.ru");
		userCredentials7.setFirstName("user7FirstName");
		userCredentials7.setLastName("user7LastName");
		userProfile.registerUser(user7, userCredentials7);
		UserProfile user8 = new UserProfile();
		UserCredentials userCredentials8 = new UserCredentials();
		user8.setLogin("Login8");
		user8.setPassword("123456");
		userCredentials8.setEmail("user8@mail.ru");
		userCredentials8.setFirstName("user8FirstName");
		userCredentials8.setLastName("user8LastName");
		userProfile.registerUser(user8, userCredentials8);
		UserProfile user9 = new UserProfile();
		UserCredentials userCredentials9 = new UserCredentials();
		user9.setLogin("Login9");
		user9.setPassword("123456");
		userCredentials9.setEmail("user9@mail.ru");
		userCredentials9.setFirstName("user9FirstName");
		userCredentials9.setLastName("user9LastName");
		userProfile.registerUser(user9, userCredentials9);
		UserProfile user10 = new UserProfile();
		UserCredentials userCredentials10 = new UserCredentials();
		user10.setLogin("Login10");
		user10.setPassword("123456");
		userCredentials10.setEmail("user10@mail.ru");
		userCredentials10.setFirstName("user10FirstName");
		userCredentials10.setLastName("user10LastName");
		userProfile.registerUser(user10, userCredentials10);
		UserProfile user11 = new UserProfile();
		UserCredentials userCredentials11 = new UserCredentials();
		user11.setLogin("Login11");
		user11.setPassword("123456");
		userCredentials11.setEmail("user11@mail.ru");
		userCredentials11.setFirstName("user11FirstName");
		userCredentials11.setLastName("user11LastName");
		userProfile.registerUser(user11, userCredentials11);
		UserProfile user12 = new UserProfile();
		UserCredentials userCredentials12 = new UserCredentials();
		user12.setLogin("Login12");
		user12.setPassword("123456");
		userCredentials12.setEmail("user12@mail.ru");
		userCredentials12.setFirstName("user12FirstName");
		userCredentials12.setLastName("user12LastName");
		userProfile.registerUser(user12, userCredentials12);
		UserProfile user13 = new UserProfile();
		UserCredentials userCredentials13 = new UserCredentials();
		user13.setLogin("Login13");
		user13.setPassword("123456");
		userCredentials13.setEmail("user13@mail.ru");
		userCredentials13.setFirstName("user13FirstName");
		userCredentials13.setLastName("user13LastName");
		userProfile.registerUser(user13, userCredentials13);
		UserProfile user14 = new UserProfile();
		UserCredentials userCredentials14 = new UserCredentials();
		user14.setLogin("Login14");
		user14.setPassword("123456");
		userCredentials14.setEmail("user14@mail.ru");
		userCredentials14.setFirstName("user14FirstName");
		userCredentials14.setLastName("user14LastName");
		userProfile.registerUser(user14, userCredentials14);
		UserProfile user15 = new UserProfile();
		UserCredentials userCredentials15 = new UserCredentials();
		user15.setLogin("Login15");
		user15.setPassword("123456");
		userCredentials15.setEmail("user15@mail.ru");
		userCredentials15.setFirstName("user15FirstName");
		userCredentials15.setLastName("user15LastName");
		userProfile.registerUser(user15, userCredentials15);
		UserProfile user16 = new UserProfile();
		UserCredentials userCredentials16 = new UserCredentials();
		user16.setLogin("Login16");
		user16.setPassword("123456");
		userCredentials16.setEmail("user16@mail.ru");
		userCredentials16.setFirstName("user16FirstName");
		userCredentials16.setLastName("user16LastName");
		userProfile.registerUser(user16, userCredentials16);
		UserProfile user17 = new UserProfile();
		UserCredentials userCredentials17 = new UserCredentials();
		user17.setLogin("Login17");
		user17.setPassword("123456");
		userCredentials17.setEmail("user17@mail.ru");
		userCredentials17.setFirstName("user17FirstName");
		userCredentials17.setLastName("user17LastName");
		userProfile.registerUser(user17, userCredentials17);
		UserProfile user18 = new UserProfile();
		UserCredentials userCredentials18 = new UserCredentials();
		user18.setLogin("Login18");
		user18.setPassword("123456");
		userCredentials18.setEmail("user18@mail.ru");
		userCredentials18.setFirstName("user18FirstName");
		userCredentials18.setLastName("user18LastName");
		userProfile.registerUser(user18, userCredentials18);
		UserProfile user19 = new UserProfile();
		UserCredentials userCredentials19 = new UserCredentials();
		user19.setLogin("Login19");
		user19.setPassword("123456");
		userCredentials19.setEmail("user19@mail.ru");
		userCredentials19.setFirstName("user19FirstName");
		userCredentials19.setLastName("user19LastName");
		userProfile.registerUser(user19, userCredentials19);
		UserProfile user20 = new UserProfile();
		UserCredentials userCredentials20 = new UserCredentials();
		user20.setLogin("Login20");
		user20.setPassword("123456");
		userCredentials20.setEmail("user20@mail.ru");
		userCredentials20.setFirstName("user20FirstName");
		userCredentials20.setLastName("user20LastName");
		userProfile.registerUser(user20, userCredentials20);
		System.out.println("Юзеры готовы");

		Type type1 = new Type();
		type1.setIdUser(user1);
		type1.setTypeName("Электроника и бытовая техника");
		typeService.registerType(type1);
		Type type2 = new Type();
		type2.setIdUser(user1);
		type2.setTypeName("Компьютерная техника");
		typeService.registerType(type2);
		Type type3 = new Type();
		type3.setIdUser(user1);
		type3.setTypeName("Телефоны и связь");
		typeService.registerType(type3);
		Type type4 = new Type();
		type4.setIdUser(user1);
		type4.setTypeName("Фото, видео");
		typeService.registerType(type4);
		Type type5 = new Type();
		type5.setIdUser(user1);
		type5.setTypeName("Мебель, интерьер, обиход");
		typeService.registerType(type5);
		Type type6 = new Type();
		type6.setIdUser(user1);
		type6.setTypeName("Одежда, обувь, аксессуары");
		typeService.registerType(type6);
		Type type7 = new Type();
		type7.setIdUser(user1);
		type7.setTypeName("Животные и растения");
		typeService.registerType(type7);
		Type type8 = new Type();
		type8.setIdUser(user1);
		type8.setTypeName("Книга, учебники, журналы");
		typeService.registerType(type8);
		Type type9 = new Type();
		type9.setIdUser(user1);
		type9.setTypeName("Продукты питания");
		typeService.registerType(type9);
		Type type10 = new Type();
		type10.setIdUser(user1);
		type10.setTypeName("Строительные материалы");
		typeService.registerType(type10);
		Type type11 = new Type();
		type11.setIdUser(user1);
		type11.setTypeName("Запрещенный для ввоза");
		typeService.registerType(type11);
		Type type12 = new Type();
		type12.setIdUser(user1);
		type12.setTypeName("другие");
		typeService.registerType(type12);
		Type type13 = new Type();
		type13.setIdUser(user1);
		type13.setTypeName("Кухонная техника");
		type13.setParentType(type1);
		typeService.registerType(type13);
		Type type14 = new Type();
		type14.setIdUser(user1);
		type14.setTypeName("Ноутбуки");
		type14.setParentType(type2);
		typeService.registerType(type14);
		Type type15 = new Type();
		type15.setIdUser(user1);
		type15.setTypeName("Мобильные телефоны");
		type15.setParentType(type3);
		typeService.registerType(type15);
		Type type16 = new Type();
		type16.setIdUser(user1);
		type16.setTypeName("Фото-, видеокамеры, объективы");
		type16.setParentType(type4);
		typeService.registerType(type16);
		Type type17 = new Type();
		type17.setIdUser(user1);
		type17.setTypeName("Мебель для гостиной");
		type17.setParentType(type5);
		typeService.registerType(type17);
		Type type18 = new Type();
		type18.setIdUser(user1);
		type18.setTypeName("Бижутерия, аксессуары");
		type18.setParentType(type6);
		typeService.registerType(type18);
		Type type19 = new Type();
		type19.setIdUser(user1);
		type19.setTypeName("Растения");
		type19.setParentType(type7);
		typeService.registerType(type19);
		Type type20 = new Type();
		type20.setIdUser(user1);
		type20.setTypeName("Чай, кофе, напитки");
		type20.setParentType(type9);
		typeService.registerType(type20);
		Type type21 = new Type();
		type21.setIdUser(user1);
		type21.setTypeName("Растворы");
		type21.setParentType(type10);
		typeService.registerType(type21);
		System.out.println("Типы готовы");

		long idUser = 1;
		Recipient recipient;
		for (int i = 0; i < 400; i++) {

			recipient = new Recipient();
			recipient.setName("ФИО " + i);
			recipient.setAddress("Адрес " + i);
			recipient.setCity("Город " + i);
			if (i % 20 == 0) {
				idUser++;
			}
			recipient.setIdUser(userProfile.getUser(idUser));
			recipientService.registerRecipient(recipient);
		}
		System.out.println("Получатели готовы");
		Product product;
		BigDecimal price = new BigDecimal("100000");
		long idType = 0;
		long idUserProd = 1;
		TypeFilter filter = new TypeFilter();
		filter.setFetchParentType(true);
		filter.setId(idType);
		for (int i = 0; i < 2000; i++) {

			product = new Product();
			if (i % 1000 == 0) {
				idUserProd++;
			}
			product.setIdUser(userProfile.getUser(idUserProd));
			product.setNameProduct("ИмяПродукта " + i);
			product.setPriceProduct(price);
			product.setStatus(true);
			product.setWeight(7.0);
			if (i % 500 == 0) {
				idType++;
				if (idType == 11L) {
					idType++;
				}
				if (idType == 22) {
					idType = 1;
				}
				filter.setId(idType);
			}
			product.setTypes(typeService.findType(filter).get(0));
			productService.registerProduct(product);
		}
		System.out.println("Продукты готовы");
		Package pack;
		long idPack = System.currentTimeMillis();
		BigDecimal fine = new BigDecimal("0");
		BigDecimal percentFine = new BigDecimal("20");
		BigDecimal pricePack = new BigDecimal("200000");
		long idRec = 0;
		List<Product> listProd = new ArrayList<>();
		long idProd = 0;
		long idUserPack = 1;
		for (int i = 0; i < 20000; i++) {

			pack = new Package();
			pack.setId(idPack + i);
			pack.setCountrySender("Страна " + i);
			pack.setPenalty(fine);
			if (i % 1000 == 0) {
				idUserPack++;
			}
			pack.setIdUser(userProfile.getUser(idUserPack));
			pack.setPaid(false);
			pack.setPaymentDeadline("10");
			pack.setPercentPenalty(percentFine);
			pack.setPrice(pricePack);
			pack.setTax(fine);
			pack.setWeight(14.0);
			if (i % 50 == 0) {
				idRec++;
			}
			pack.setRecipient(recipientService.getRecipient(idRec));
			if (i % 10 == 0) {
				idProd = idProd + 2;
				listProd.clear();
				listProd.add(productService.getProductWithId(idProd));
				listProd.add(productService.getProductWithId(idProd - 1L));
			}
			pack.setProducts(listProd);
			packageService.registerPackage(pack);
		}
		System.out.println("Пакеты готовы");
		Package packUpdate;
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		int day = 1;
		int month = 0;
		int year = 2012;
		for (int i = 0; i < 20000; i++) {
			packUpdate = packageService.getPackageWithId(idPack + i);
			if (i % 36 == 0) {
				if (day == 30) {
					day = 0;
				}
				day++;
			}
			cal.set(Calendar.DAY_OF_MONTH, day);
			if (i % 1080 == 0) {
				if (month == 11) {
					month = -1;
				}
				month++;
			}
			cal.set(Calendar.MONTH, month);
			if (i % 12960 == 0) {
				year++;
			}
			cal.set(Calendar.YEAR, year);
			d.setTime(cal.getTime().getTime());
			packUpdate.setDate(d);
			packageService.updatePackage(packUpdate);

		}
		System.out.println("Даты обновлены");

		// Recipient recipient2;
		// for (int i = 2000; i < 4000; i++) {
		//
		// recipient2 = new Recipient();
		// recipient2.setName("ФИО " + i);
		// recipient2.setAddress("Адрес " + i);
		// recipient2.setCity("Город " + i);
		// recipient2.setIdUser(user2);
		// recipientService.register(recipient2);
		// }
		// Product product2;
		// BigDecimal price2 = new BigDecimal("100000");
		// long idType2 = 0;
		// for (int i = 10000; i < 20000; i++) {
		//
		// product2 = new Product();
		// product2.setIdUser(user2);
		// product2.setNameProduct("ИмяПродукта " + i);
		// product2.setPriceProduct(price2);
		// product2.setStatus(true);
		// product2.setWeight(7.0);
		// if (i % 500 == 0) {
		// idType2++;
		// if (idType2 == 11L) {
		// idType2++;
		// }
		// }
		// product2.setTypes(typeService.get(idType2));
		// productService.register(product2);
		// }
		// Package pack2;
		// long idPack2 = System.currentTimeMillis();
		// BigDecimal fine2 = new BigDecimal("0");
		// BigDecimal percentFine2 = new BigDecimal("20");
		// BigDecimal pricePack2 = new BigDecimal("200000");
		// long idRec2 = 2000;
		// List<Product> listProd2 = new ArrayList<>();
		// long idProd2 = 9999;
		// for (int i = 20000; i < 40000; i++) {
		//
		// pack2 = new Package();
		// pack2.setId(idPack2 + i);
		// pack2.setCountrySender("Страна " + i);
		// pack2.setFine(fine2);
		// pack2.setIdUser(user2);
		// pack2.setPaid(false);
		// pack2.setPaymentDeadline("10");
		// pack2.setPercentFine(percentFine2);
		// pack2.setPrice(pricePack2);
		// pack2.setTax(fine2);
		// pack2.setWeight(14.0);
		// if (i % 10 == 0 && idRec2 != 2000) {
		// idRec2++;
		// }
		// pack2.setIdRecipient(recipientService.getRecipient(idRec2));
		// if (i % 2 == 0) {
		// idProd2 = idProd2 + 2;
		// listProd2.clear();
		// listProd2.add(productService.get(idProd2));
		// listProd2.add(productService.get(idProd2 - 1L));
		// }
		// pack2.setProducts(listProd2);
		// packageService.register(pack2);
		// }
		//
		// Recipient recipient3;
		// for (int i = 4000; i < 6000; i++) {
		//
		// recipient3 = new Recipient();
		// recipient3.setName("ФИО " + i);
		// recipient3.setAddress("Адрес " + i);
		// recipient3.setCity("Город " + i);
		// recipient3.setIdUser(user3);
		// recipientService.register(recipient3);
		// }
		// Product product3;
		// BigDecimal price3 = new BigDecimal("100000");
		// long idType3 = 0;
		// for (int i = 20000; i < 30000; i++) {
		//
		// product3 = new Product();
		// product3.setIdUser(user3);
		// product3.setNameProduct("ИмяПродукта " + i);
		// product3.setPriceProduct(price3);
		// product3.setStatus(true);
		// product3.setWeight(7.0);
		// if (i % 500 == 0) {
		// idType3++;
		// if (idType3 == 11L) {
		// idType3++;
		// }
		// }
		// product3.setTypes(typeService.get(idType3));
		// productService.register(product3);
		// }
		// Package pack3;
		// long idPack3 = System.currentTimeMillis();
		// BigDecimal fine3 = new BigDecimal("0");
		// BigDecimal percentFine3 = new BigDecimal("20");
		// BigDecimal pricePack3 = new BigDecimal("200000");
		// long idRec3 = 4000;
		// List<Product> listProd3 = new ArrayList<>();
		// long idProd3 = 19999;
		// for (int i = 40000; i < 60000; i++) {
		//
		// pack3 = new Package();
		// pack3.setId(idPack3 + i);
		// pack3.setCountrySender("Страна " + i);
		// pack3.setFine(fine3);
		// pack3.setIdUser(user3);
		// pack3.setPaid(false);
		// pack3.setPaymentDeadline("10");
		// pack3.setPercentFine(percentFine3);
		// pack3.setPrice(pricePack3);
		// pack3.setTax(fine3);
		// pack3.setWeight(14.0);
		// if (i % 10 == 0 && idRec3 != 4000) {
		// idRec3++;
		// }
		// pack3.setIdRecipient(recipientService.getRecipient(idRec3));
		// if (i % 2 == 0) {
		// idProd3 = idProd3 + 2;
		// listProd3.clear();
		// listProd3.add(productService.get(idProd3));
		// listProd3.add(productService.get(idProd3 - 1L));
		// }
		// pack3.setProducts(listProd3);
		// packageService.register(pack3);
		// }
		//
		// Recipient recipient4;
		// for (int i = 6000; i < 8000; i++) {
		//
		// recipient4 = new Recipient();
		// recipient4.setName("ФИО " + i);
		// recipient4.setAddress("Адрес " + i);
		// recipient4.setCity("Город " + i);
		// recipient4.setIdUser(user4);
		// recipientService.register(recipient4);
		// }
		// Product product4;
		// BigDecimal price4 = new BigDecimal("100000");
		// long idType4 = 0;
		// for (int i = 30000; i < 40000; i++) {
		//
		// product4 = new Product();
		// product4.setIdUser(user4);
		// product4.setNameProduct("ИмяПродукта " + i);
		// product4.setPriceProduct(price4);
		// product4.setStatus(true);
		// product4.setWeight(7.0);
		// if (i % 500 == 0) {
		// idType4++;
		// if (idType4 == 11L) {
		// idType4++;
		// }
		// }
		// product4.setTypes(typeService.get(idType4));
		// productService.register(product4);
		// }
		// Package pack4;
		// long idPack4 = System.currentTimeMillis();
		// BigDecimal fine4 = new BigDecimal("0");
		// BigDecimal percentFine4 = new BigDecimal("20");
		// BigDecimal pricePack4 = new BigDecimal("200000");
		// long idRec4 = 6000;
		// List<Product> listProd4 = new ArrayList<>();
		// long idProd4 = 29999;
		// for (int i = 60000; i < 80000; i++) {
		//
		// pack4 = new Package();
		// pack4.setId(idPack4 + i);
		// pack4.setCountrySender("Страна " + i);
		// pack4.setFine(fine4);
		// pack4.setIdUser(user4);
		// pack4.setPaid(false);
		// pack4.setPaymentDeadline("10");
		// pack4.setPercentFine(percentFine4);
		// pack4.setPrice(pricePack4);
		// pack4.setTax(fine4);
		// pack4.setWeight(14.0);
		// if (i % 10 == 0 && idRec4 != 6000) {
		// idRec4++;
		// }
		// pack4.setIdRecipient(recipientService.getRecipient(idRec4));
		// if (i % 2 == 0) {
		// idProd4 = idProd4 + 2;
		// listProd4.clear();
		// listProd4.add(productService.get(idProd4));
		// listProd4.add(productService.get(idProd4 - 1L));
		// }
		// pack4.setProducts(listProd4);
		// packageService.register(pack4);
		// }
		//
		// Recipient recipient5;
		// for (int i = 8000; i < 10000; i++) {
		//
		// recipient5 = new Recipient();
		// recipient5.setName("ФИО " + i);
		// recipient5.setAddress("Адрес " + i);
		// recipient5.setCity("Город " + i);
		// recipient5.setIdUser(user5);
		// recipientService.register(recipient5);
		// }
		// Product product5;
		// BigDecimal price5 = new BigDecimal("100000");
		// long idType5 = 0;
		// for (int i = 40000; i < 50000; i++) {
		//
		// product5 = new Product();
		// product5.setIdUser(user5);
		// product5.setNameProduct("ИмяПродукта " + i);
		// product5.setPriceProduct(price5);
		// product5.setStatus(true);
		// product5.setWeight(7.0);
		// if (i % 500 == 0) {
		// idType5++;
		// if (idType5 == 11L) {
		// idType5++;
		// }
		// }
		// product5.setTypes(typeService.get(idType5));
		// productService.register(product5);
		// }
		// Package pack5;
		// long idPack5 = System.currentTimeMillis();
		// BigDecimal fine5 = new BigDecimal("0");
		// BigDecimal percentFine5 = new BigDecimal("20");
		// BigDecimal pricePack5 = new BigDecimal("200000");
		// long idRec5 = 8000;
		// List<Product> listProd5 = new ArrayList<>();
		// long idProd5 = 39999;
		// for (int i = 80000; i < 100000; i++) {
		//
		// pack5 = new Package();
		// pack5.setId(idPack5 + i);
		// pack5.setCountrySender("Страна " + i);
		// pack5.setFine(fine5);
		// pack5.setIdUser(user5);
		// pack5.setPaid(false);
		// pack5.setPaymentDeadline("10");
		// pack5.setPercentFine(percentFine5);
		// pack5.setPrice(pricePack5);
		// pack5.setTax(fine5);
		// pack5.setWeight(14.0);
		// if (i % 10 == 0 && idRec5 != 8000) {
		// idRec5++;
		// }
		// pack5.setIdRecipient(recipientService.getRecipient(idRec5));
		// if (i % 2 == 0) {
		// idProd5 = idProd5 + 2;
		// listProd5.clear();
		// listProd5.add(productService.get(idProd5));
		// listProd5.add(productService.get(idProd5 - 1L));
		// }
		// pack5.setProducts(listProd5);
		// packageService.register(pack5);
		// }
		//
		// Recipient recipient6;
		// for (int i = 10000; i < 12000; i++) {
		//
		// recipient6 = new Recipient();
		// recipient6.setName("ФИО " + i);
		// recipient6.setAddress("Адрес " + i);
		// recipient6.setCity("Город " + i);
		// recipient6.setIdUser(user6);
		// recipientService.register(recipient6);
		// }
		// Product product6;
		// BigDecimal price6 = new BigDecimal("100000");
		// long idType6 = 0;
		// for (int i = 50000; i < 60000; i++) {
		//
		// product6 = new Product();
		// product6.setIdUser(user6);
		// product6.setNameProduct("ИмяПродукта " + i);
		// product6.setPriceProduct(price6);
		// product6.setStatus(true);
		// product6.setWeight(7.0);
		// if (i % 500 == 0) {
		// idType6++;
		// if (idType6 == 11L) {
		// idType6++;
		// }
		// }
		// product6.setTypes(typeService.get(idType6));
		// productService.register(product6);
		// }
		// Package pack6;
		// long idPack6 = System.currentTimeMillis();
		// BigDecimal fine6 = new BigDecimal("0");
		// BigDecimal percentFine6 = new BigDecimal("20");
		// BigDecimal pricePack6 = new BigDecimal("200000");
		// long idRec6 = 10000;
		// List<Product> listProd6 = new ArrayList<>();
		// long idProd6 = 49999;
		// for (int i = 100000; i < 120000; i++) {
		//
		// pack6 = new Package();
		// pack6.setId(idPack6 + i);
		// pack6.setCountrySender("Страна " + i);
		// pack6.setFine(fine6);
		// pack6.setIdUser(user6);
		// pack6.setPaid(false);
		// pack6.setPaymentDeadline("10");
		// pack6.setPercentFine(percentFine6);
		// pack6.setPrice(pricePack6);
		// pack6.setTax(fine6);
		// pack6.setWeight(14.0);
		// if (i % 10 == 0 && idRec6 != 10000) {
		// idRec6++;
		// }
		// pack6.setIdRecipient(recipientService.getRecipient(idRec6));
		// if (i % 2 == 0) {
		// idProd6 = idProd6 + 2;
		// listProd6.clear();
		// listProd6.add(productService.get(idProd6));
		// listProd6.add(productService.get(idProd6 - 1L));
		// }
		// pack6.setProducts(listProd6);
		// packageService.register(pack6);
		// }
		//
		// Recipient recipient7;
		// for (int i = 12000; i < 14000; i++) {
		//
		// recipient7 = new Recipient();
		// recipient7.setName("ФИО " + i);
		// recipient7.setAddress("Адрес " + i);
		// recipient7.setCity("Город " + i);
		// recipient7.setIdUser(user7);
		// recipientService.register(recipient7);
		// }
		// Product product7;
		// BigDecimal price7 = new BigDecimal("100000");
		// long idType7 = 0;
		// for (int i = 60000; i < 70000; i++) {
		//
		// product7 = new Product();
		// product7.setIdUser(user7);
		// product7.setNameProduct("ИмяПродукта " + i);
		// product7.setPriceProduct(price7);
		// product7.setStatus(true);
		// product7.setWeight(7.0);
		// if (i % 500 == 0) {
		// idType7++;
		// if (idType7 == 11L) {
		// idType7++;
		// }
		// }
		// product7.setTypes(typeService.get(idType7));
		// productService.register(product7);
		// }
		// Package pack7;
		// long idPack7 = System.currentTimeMillis();
		// BigDecimal fine7 = new BigDecimal("0");
		// BigDecimal percentFine7 = new BigDecimal("20");
		// BigDecimal pricePack7 = new BigDecimal("200000");
		// long idRec7 = 12000;
		// List<Product> listProd7 = new ArrayList<>();
		// long idProd7 = 59999;
		// for (int i = 120000; i < 140000; i++) {
		//
		// pack7 = new Package();
		// pack7.setId(idPack7 + i);
		// pack7.setCountrySender("Страна " + i);
		// pack7.setFine(fine7);
		// pack7.setIdUser(user7);
		// pack7.setPaid(false);
		// pack7.setPaymentDeadline("10");
		// pack7.setPercentFine(percentFine7);
		// pack7.setPrice(pricePack7);
		// pack7.setTax(fine7);
		// pack7.setWeight(14.0);
		// if (i % 10 == 0 && idRec7 != 12000) {
		// idRec7++;
		// }
		// pack7.setIdRecipient(recipientService.getRecipient(idRec7));
		// if (i % 2 == 0) {
		// idProd7 = idProd7 + 2;
		// listProd7.clear();
		// listProd7.add(productService.get(idProd7));
		// listProd7.add(productService.get(idProd7 - 1L));
		// }
		// pack7.setProducts(listProd7);
		// packageService.register(pack7);
		// }
		//
		// Recipient recipient8;
		// for (int i = 14000; i < 16000; i++) {
		//
		// recipient8 = new Recipient();
		// recipient8.setName("ФИО " + i);
		// recipient8.setAddress("Адрес " + i);
		// recipient8.setCity("Город " + i);
		// recipient8.setIdUser(user8);
		// recipientService.register(recipient8);
		// }
		// Product product8;
		// BigDecimal price8 = new BigDecimal("100000");
		// long idType8 = 0;
		// for (int i = 70000; i < 80000; i++) {
		//
		// product8 = new Product();
		// product8.setIdUser(user8);
		// product8.setNameProduct("ИмяПродукта " + i);
		// product8.setPriceProduct(price8);
		// product8.setStatus(true);
		// product8.setWeight(7.0);
		// if (i % 500 == 0) {
		// idType8++;
		// if (idType8 == 11L) {
		// idType8++;
		// }
		// }
		// product8.setTypes(typeService.get(idType8));
		// productService.register(product8);
		// }
		// Package pack8;
		// long idPack8 = System.currentTimeMillis();
		// BigDecimal fine8 = new BigDecimal("0");
		// BigDecimal percentFine8 = new BigDecimal("20");
		// BigDecimal pricePack8 = new BigDecimal("200000");
		// long idRec8 = 14000;
		// List<Product> listProd8 = new ArrayList<>();
		// long idProd8 = 69999;
		// for (int i = 140000; i < 160000; i++) {
		//
		// pack8 = new Package();
		// pack8.setId(idPack8 + i);
		// pack8.setCountrySender("Страна " + i);
		// pack8.setFine(fine8);
		// pack8.setIdUser(user8);
		// pack8.setPaid(false);
		// pack8.setPaymentDeadline("10");
		// pack8.setPercentFine(percentFine8);
		// pack8.setPrice(pricePack8);
		// pack8.setTax(fine8);
		// pack8.setWeight(14.0);
		// if (i % 10 == 0 && idRec8 != 14000) {
		// idRec8++;
		// }
		// pack8.setIdRecipient(recipientService.getRecipient(idRec8));
		// if (i % 2 == 0) {
		// idProd8 = idProd8 + 2;
		// listProd8.clear();
		// listProd8.add(productService.get(idProd8));
		// listProd8.add(productService.get(idProd8 - 1L));
		// }
		// pack8.setProducts(listProd8);
		// packageService.register(pack8);
		// }
	}

}
