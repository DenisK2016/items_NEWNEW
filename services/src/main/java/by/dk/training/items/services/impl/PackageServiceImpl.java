package by.dk.training.items.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.dk.training.items.dataaccess.PackageDao;
import by.dk.training.items.dataaccess.RecipientDao;
import by.dk.training.items.dataaccess.UserProfileDao;
import by.dk.training.items.dataaccess.filters.PackageFilter;
import by.dk.training.items.datamodel.Package;
import by.dk.training.items.datamodel.Recipient;
import by.dk.training.items.services.PackageService;

@Service
public class PackageServiceImpl implements PackageService {

	private static Logger LOGGER = LoggerFactory.getLogger(PackageServiceImpl.class);
	@Inject
	private PackageDao packDao;
	@Inject
	private UserProfileDao userDao;
	@Inject
	private RecipientDao recipientDao;
	private Date date;

	@Override
	public void registerPackage(Package pack) {
		dateReset(pack);
		pack.setDate(date);
		packDao.insert(pack);
		Recipient rec = pack.getRecipient();
		rec.setCounterPackages(1);
		recipientDao.update(rec);
		userDao.update(pack.getIdUser());
		LOGGER.info("Package was registered: {}", pack);
	}

	@Override
	public Package getPackageWithId(Long id) {
		LOGGER.info("Package was selected: {}", packDao.get(id));
		return packDao.get(id);
	}

	@Override
	public void updatePackage(Package pack) {
		LOGGER.info("Package update, new and old: {}", pack, packDao.get(pack.getId()));
		dateReset(pack);
		pack.setDate(date);
		this.packDao.update(pack);
	}

	private void dateReset(Package pack) {
		if (pack.getDate() == null) {
			date = new Date();
		} else {
			date = pack.getDate();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date.setTime(cal.getTime().getTime());
	}

	@Override
	public void deletePackageWithId(Long id) {
		LOGGER.info("Package delete: {}", packDao.get(id));
		packDao.delete(id);
	}

	@Override
	public List<Package> findPackage(PackageFilter packageFilter) {
		LOGGER.info("Package find by filter: {}", packageFilter);
		return packDao.findPackage(packageFilter);
	}

	@Override
	public List<Package> getAll() {
		LOGGER.info("Package getAll: {}", "All packages");
		return packDao.getAll();
	}

	@Override
	public Long overallNumberOfPackages(PackageFilter packagefilter) {
		LOGGER.info("Package count(): {}", packagefilter);
		return packDao.overallNumberOfPackages(packagefilter);
	}

	@Override
	public List<Package> getPackagesBetweenDates(Date startDate, Date endDate) {
		return packDao.extractPackagesBetweenDates(startDate, endDate);
	}

	@Override
	public Package maxPricePackage() {
		LOGGER.info("Package with maxPrice: {}");
		Package extractPackageWithMaxPrice = packDao.extractPackageWithMaxPrice();
		if (extractPackageWithMaxPrice == null) {
			return new Package();
		}
		return extractPackageWithMaxPrice;
	}

	@Override
	public Long numberOfPacksWithRecipientBetweenDates(PackageFilter packageFilter) {
		return packDao.numberOfPacksWithRecipientBetweenDates(packageFilter);
	}

	@Override
	public long overallNumberOfPackages() {
		return packDao.overallNumberOfPackages();
	}

	@Override
	public long numberOfPackagesBetweenDates(Date startDate, Date endDate) {
		return packDao.numberOfPackagesBetweenDates(startDate, endDate);
	}

	@Override
	public String getMostPopularCountry() {
		return packDao.getMostPopularCountry();
	}

	@Override
	public List<Package> betweenDatesRecipient(PackageFilter packageFilter) {
		return packDao.betweenDatesRecipient(packageFilter);
	}

}
