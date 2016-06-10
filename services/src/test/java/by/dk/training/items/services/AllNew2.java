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

import by.dk.training.items.dataaccess.filters.ProductFilter;
import by.dk.training.items.dataaccess.filters.RecipientFilter;
import by.dk.training.items.dataaccess.filters.TypeFilter;
import by.dk.training.items.dataaccess.filters.UserFilter;
import by.dk.training.items.datamodel.Package;
import by.dk.training.items.datamodel.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class AllNew2 {

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
	public void testProductCreate() {
		Product product = new Product();
		TypeFilter filter = new TypeFilter();

		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Lenovo Z510");
		product.setPriceProduct(new BigDecimal("10000000"));
		product.setStatus(true);
		filter.setTypeName("Ноутбуки");
		product.setTypes(typeService.find(filter).get(0));
		product.setWeight(5.0);
		productService.register(product);

		product.setIdUser(userProfileService.getUser((long) 3));
		product.setNameProduct("Nokia 6600");
		product.setPriceProduct(new BigDecimal("3000000"));
		product.setStatus(true);
		filter.setTypeName("Мобильные телефоны");
		product.setTypes(typeService.find(filter).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setIdUser(userProfileService.getUser((long) 4));
		product.setNameProduct("Gefest 3424");
		product.setPriceProduct(new BigDecimal("15000000"));
		product.setStatus(true);
		filter.setTypeName("Кухонная техника");
		product.setTypes(typeService.find(filter).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Samsung GT7000+");
		product.setPriceProduct(new BigDecimal("4000000"));
		product.setStatus(true);
		filter.setTypeName("Фото-, видеокамеры, объективы");
		product.setTypes(typeService.find(filter).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Командор-343");
		product.setPriceProduct(new BigDecimal("7000000"));
		product.setStatus(true);
		filter.setTypeName("Мебель для гостиной");
		product.setTypes(typeService.find(filter).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Спортивная сумка-SPORT 3000");
		product.setPriceProduct(new BigDecimal("1000000"));
		product.setStatus(true);
		filter.setTypeName("Бижутерия, аксессуары");
		product.setTypes(typeService.find(filter).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Кактус");
		product.setPriceProduct(new BigDecimal("500000"));
		product.setStatus(true);
		filter.setTypeName("Растения");
		product.setTypes(typeService.find(filter).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Nescafe");
		product.setPriceProduct(new BigDecimal("70000"));
		product.setStatus(true);
		filter.setTypeName("Чай, кофе, напитки");
		product.setTypes(typeService.find(filter).get(0));
		product.setWeight(5.0);
		productService.register(product);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Цемент М-500");
		product.setPriceProduct(new BigDecimal("50000"));
		product.setStatus(true);
		filter.setTypeName("Растворы");
		product.setTypes(typeService.find(filter).get(0));
		product.setWeight(50.0);
		productService.register(product);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Бампер");
		product.setPriceProduct(new BigDecimal("10000000"));
		product.setStatus(true);
		filter.setTypeName("другие");
		product.setTypes(typeService.find(filter).get(0));
		product.setWeight(5.0);
		productService.register(product);
		productService.register(product);
		product.setIdUser(userProfileService.getUser((long) 2));
		product.setNameProduct("Какаин");
		product.setPriceProduct(new BigDecimal("10000000"));
		product.setStatus(true);
		filter.setTypeName("Запрещенный для ввоза");
		product.setTypes(typeService.find(filter).get(0));
		product.setWeight(5.0);
		productService.register(product);

	}

	@Test
	public void testPackageCreate() {
		Package pack = new Package();
		UserFilter userFilter = new UserFilter();
		RecipientFilter recipientFilter = new RecipientFilter();
		ProductFilter productFilter = new ProductFilter();
		BigDecimal fine = new BigDecimal("0");
		long idPack = System.currentTimeMillis();

		for (int k = 1; k <= 10; k++) {
			for (int i = 1; i <= 1000; i++) {

				pack.setId(idPack++);
				pack.setCountrySender("Country " + i);
				pack.setFine(fine);
				int n = 1;
				int nn = 1;
				if (i % 10 == 0) {
					n++;
					nn++;
				}
				recipientFilter.setId((long) n);
				pack.setIdRecipient(recipientService.find(recipientFilter).get(0));
				userFilter.setId((long) nn);
				pack.setIdUser(userProfileService.find(userFilter).get(0));
				pack.setPaid(false);
				pack.setPaymentDeadline("10");
				int nnn = 1;
				if (i % 100 == 0) {
					nnn++;
				}
				productFilter.setId((long) (nnn));
				pack.setProducts(productService.find(productFilter).get(0));
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
			if (count % 3333 == 0) {
				cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
			}
			if (count % 277 == 0) {
				if (m == 12) {
					m = 0;
				}
				cal.set(Calendar.MONTH, m);
				m++;
			}
			if (count % 9 == 0) {
				if (day == 31) {
					day = 1;
				}
				cal.set(Calendar.DAY_OF_MONTH, day);
			}
			d.setTime(cal.getTime().getTime());
			packageService.update(p);
		}

	}

}
