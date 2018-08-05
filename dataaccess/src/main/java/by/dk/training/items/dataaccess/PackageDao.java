package by.dk.training.items.dataaccess;

import java.util.Date;
import java.util.List;

import by.dk.training.items.dataaccess.filters.PackageFilter;
import by.dk.training.items.datamodel.Package;

public interface PackageDao extends AbstractDao<Package, Long> {

	Long overallNumberOfPackages(PackageFilter filter);

	List<Package> findPackage(PackageFilter filter);

	List<Package> extractPackagesBetweenDates(Date startDate, Date endDate);

	Package extractPackageWithMaxPrice();

	Long numberOfPacksWithRecipientBetweenDates(PackageFilter filter);

	List<Package> betweenDatesRecipient(PackageFilter filter);

	long overallNumberOfPackages();

	long numberOfPackagesBetweenDates(Date start, Date end);

	String getMostPopularCountry();

}
