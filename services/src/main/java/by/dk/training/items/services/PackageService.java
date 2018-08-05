package by.dk.training.items.services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import by.dk.training.items.dataaccess.filters.PackageFilter;
import by.dk.training.items.datamodel.Package;

public interface PackageService {

	@Transactional
	void registerPackage(Package pack);

	Package getPackageWithId(Long id);

	@Transactional
	void updatePackage(Package pack);

	@Transactional
	void deletePackageWithId(Long trackingCode);

	List<Package> findPackage(PackageFilter packageFilter);

	List<Package> getAll();

	Long overallNumberOfPackages(PackageFilter filter);

	List<Package> getPackagesBetweenDates(Date startDate, Date endDate);

	Long numberOfPacksWithRecipientBetweenDates(PackageFilter packageFilter);

	List<Package> betweenDatesRecipient(PackageFilter packageFilter);

	Package maxPricePackage();

	long overallNumberOfPackages();

	long numberOfPackagesBetweenDates(Date startDate, Date endDate);

	String getMostPopularCountry();
}
