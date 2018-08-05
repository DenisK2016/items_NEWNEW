package by.dk.training.items.dataaccess.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.dk.training.items.dataaccess.PackageDao;
import by.dk.training.items.dataaccess.filters.PackageFilter;
import by.dk.training.items.datamodel.Package;
import by.dk.training.items.datamodel.Package_;

@Repository
public class PackageDaoImpl extends AbstractDaoImpl<Package, Long> implements PackageDao {

	private Date startDate;
	private Date endDate;

	protected PackageDaoImpl() {
		super(Package.class);
	}

	@Override
	public List<Package> extractPackagesBetweenDates(Date startDate, Date endDate) {
		EntityManager em = getEntityManager();

		@SuppressWarnings("unchecked")
		List<Package> listPack = em.createQuery("SELECT e FROM Package e WHERE e.date BETWEEN :startDate AND :endDate")
				.setParameter("startDate", startDate, TemporalType.TIMESTAMP)
				.setParameter("endDate", endDate, TemporalType.TIMESTAMP).getResultList();

		return listPack;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Package> findPackage(PackageFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Package> criteriaQuery = criteriaBuilder.createQuery(Package.class);
		Root<Package> packageRoot = criteriaQuery.from(Package.class);
		criteriaQuery.select(packageRoot);

		boolean isCorrectFilter = isCorrectPackageFilter(filter);
		if (isCorrectFilter) {
			Predicate taxEqualCondition = criteriaBuilder.equal(packageRoot.get(Package_.tax), filter.getTax());
			Predicate idEqualCondition = criteriaBuilder.equal(packageRoot.get(Package_.id), filter.getId());
			Predicate priceEqualCondition = criteriaBuilder.equal(packageRoot.get(Package_.price), filter.getPrice());
			Predicate weightEqualCondition = criteriaBuilder.equal(packageRoot.get(Package_.weight),
					filter.getWeight());
			Predicate dateEqualCondition = criteriaBuilder.equal(packageRoot.get(Package_.date), filter.getDate());
			Predicate descrEqualCondition = criteriaBuilder.equal(packageRoot.get(Package_.description),
					filter.getDescription());
			Predicate countryEqualCondition = criteriaBuilder.equal(packageRoot.get(Package_.countrySender),
					filter.getCountrySender());
			Predicate paymentEqualCondition = criteriaBuilder.equal(packageRoot.get(Package_.paymentDeadline),
					filter.getPaymentDeadline());
			Predicate fineEqualCondition = criteriaBuilder.equal(packageRoot.get(Package_.penalty), filter.getFine());
			Predicate paidEqualCondition = criteriaBuilder.equal(packageRoot.get(Package_.paid), filter.getPaid());
			Predicate userEqualCondition = criteriaBuilder.equal(packageRoot.get(Package_.idUser), filter.getUser());
			Predicate recipientEqualCondition = criteriaBuilder.equal(packageRoot.get(Package_.recipient),
					filter.getRecipint());
			Predicate betweenDate = null;
			if ((filter.getStartDate() != null) && (filter.getEndDate() != null)) {
				betweenDate = criteriaBuilder.between(packageRoot.get(Package_.date), filter.getStartDate(),
						filter.getEndDate());
			}
			if ((filter.getStartDate() == null) && (filter.getEndDate() != null)) {
				startDate = new Date(0, 0, 0);
				endDate = filter.getEndDate();
				betweenDate = criteriaBuilder.between(packageRoot.get(Package_.date), startDate, endDate);
			}
			if ((filter.getStartDate() != null) && (filter.getEndDate() == null)) {
				endDate = new Date();
				startDate = filter.getStartDate();
				betweenDate = criteriaBuilder.between(packageRoot.get(Package_.date), startDate, endDate);
			}

			if (filter.getProduct() != null) {
				Predicate productEqualCondition = criteriaBuilder.isMember(filter.getProduct(),
						packageRoot.get(Package_.products));
				if (betweenDate != null) {
					criteriaQuery
							.where(criteriaBuilder.or(priceEqualCondition, weightEqualCondition, dateEqualCondition,
									descrEqualCondition, countryEqualCondition, paymentEqualCondition,
									fineEqualCondition, paidEqualCondition, userEqualCondition, recipientEqualCondition,
									productEqualCondition, idEqualCondition, taxEqualCondition, betweenDate))
							.distinct(true);
				} else {
					criteriaQuery.where(criteriaBuilder.or(priceEqualCondition, weightEqualCondition,
							dateEqualCondition, descrEqualCondition, countryEqualCondition, paymentEqualCondition,
							fineEqualCondition, paidEqualCondition, userEqualCondition, recipientEqualCondition,
							productEqualCondition, idEqualCondition, taxEqualCondition)).distinct(true);
				}
			} else {
				if (betweenDate != null) {
					criteriaQuery.where(criteriaBuilder.or(priceEqualCondition, weightEqualCondition,
							dateEqualCondition, descrEqualCondition, countryEqualCondition, paymentEqualCondition,
							fineEqualCondition, paidEqualCondition, userEqualCondition, recipientEqualCondition,
							idEqualCondition, taxEqualCondition, betweenDate)).distinct(true);
				} else {
					criteriaQuery.where(criteriaBuilder.or(priceEqualCondition, weightEqualCondition,
							dateEqualCondition, descrEqualCondition, countryEqualCondition, paymentEqualCondition,
							fineEqualCondition, paidEqualCondition, userEqualCondition, recipientEqualCondition,
							idEqualCondition, taxEqualCondition)).distinct(true);
				}
			}
		}

		// set fetching
		if (filter.isFetchUser()) {
			packageRoot.fetch(Package_.idUser, JoinType.LEFT);
		}

		if (filter.isFetchRecipient()) {
			packageRoot.fetch(Package_.recipient, JoinType.LEFT);
		}

		if (filter.isFetchProduct()) {
			packageRoot.fetch(Package_.products, JoinType.LEFT);
		}

		// set sort params
		if (filter.getSortProperty() != null) {
			criteriaQuery.orderBy(new OrderImpl(packageRoot.get(filter.getSortProperty()), filter.isSortOrder()));
		}

		TypedQuery<Package> q = em.createQuery(criteriaQuery);

		// set paging
		if (filter.getOffset() != null && filter.getLimit() != null) {
			q.setFirstResult(filter.getOffset());
			q.setMaxResults(filter.getLimit());
		}

		// set execute query
		List<Package> packages = q.getResultList();

		return packages;
	}

	private boolean isCorrectPackageFilter(PackageFilter filter) {
		boolean price = (filter.getPrice() != null);
		boolean weight = (filter.getWeight() != null);
		boolean date = (filter.getDate() != null);
		boolean description = (filter.getDescription() != null);
		boolean cSender = (filter.getCountrySender() != null);
		boolean paymentDeadline = (filter.getPaymentDeadline() != null);
		boolean penalty = (filter.getFine() != null);
		boolean paid = (filter.getPaid() != null);
		boolean user = (filter.getUser() != null);
		boolean recipient = (filter.getRecipint() != null);
		boolean product = (filter.getProduct() != null);
		boolean id = (filter.getId() != null);
		boolean tax = filter.getTax() != null;
		boolean startDate = filter.getStartDate() != null;
		boolean endDate = filter.getEndDate() != null;
		boolean isCorrect = (user || recipient || product || price || weight || date || description || cSender
				|| paymentDeadline || penalty || paid || id || tax || startDate || endDate);
		return isCorrect;
	}

	@Override
	public Long overallNumberOfPackages(PackageFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Package> from = cq.from(Package.class);
		cq.select(cb.count(from));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	protected void setPaging(PackageFilter filter, TypedQuery<Package> q) {
		if (filter.getOffset() != null && filter.getLimit() != null) {
			q.setFirstResult(filter.getOffset());
			q.setMaxResults(filter.getLimit());
		}
	}

	@Override
	public Package extractPackageWithMaxPrice() {
		EntityManager em = getEntityManager();
		Package pack = (Package) em.createQuery("SELECT e FROM Package e WHERE e.price=(SELECT MAX(price) FROM e)")
				.setMaxResults(1).getSingleResult();
		return pack;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Long numberOfPacksWithRecipientBetweenDates(PackageFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Package> from = cq.from(Package.class);
		cq.select(cb.count(from));

		boolean sDate = filter.getStartDate() != null;
		boolean eDate = filter.getEndDate() != null;
		boolean recipient = (filter.getRecipint() != null);
		boolean isFilterCorrect = (recipient || sDate || eDate);

		if (isFilterCorrect) {
			Predicate recipientEqualCondition = cb.equal(from.get(Package_.recipient), filter.getRecipint());
			Predicate betweenDate = null;
			if ((sDate) && (eDate)) {
				betweenDate = cb.between(from.get(Package_.date), filter.getStartDate(), filter.getEndDate());
			}
			if ((!sDate) && (eDate)) {
				startDate = new Date(0, 0, 0);
				endDate = filter.getEndDate();
				betweenDate = cb.between(from.get(Package_.date), startDate, endDate);
			}
			if ((sDate) && (!eDate)) {
				endDate = new Date();
				startDate = filter.getStartDate();
				betweenDate = cb.between(from.get(Package_.date), startDate, endDate);
			}
			cq.where(cb.and(recipientEqualCondition, betweenDate));
		}
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public long overallNumberOfPackages() {
		EntityManager em = getEntityManager();
		String sql = "SELECT COUNT(*) FROM Package";
		Query q = em.createQuery(sql);
		long count = (long) q.getSingleResult();
		return count;
	}

	@Override
	public long numberOfPackagesBetweenDates(Date start, Date end) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Package> from = cq.from(Package.class);
		cq.select(cb.count(from));
		Predicate countBetwen = cb.between(from.get(Package_.date), start, end);
		cq.where(countBetwen);
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public String getMostPopularCountry() {
		EntityManager em = getEntityManager();
		String sql = "SELECT p.countrySender FROM Package p GROUP BY p.countrySender ORDER BY COUNT(*) DESC";
		Query q = em.createQuery(sql).setMaxResults(1);
		String country = (String) q.getSingleResult();
		return country;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Package> betweenDatesRecipient(PackageFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Package> cq = cb.createQuery(Package.class);
		Root<Package> from = cq.from(Package.class);
		cq.select(from);

		boolean sDate = filter.getStartDate() != null;
		boolean eDate = filter.getEndDate() != null;
		boolean recipient = (filter.getRecipint() != null);
		boolean isFilterCorrect = (recipient || sDate || eDate);
		if (isFilterCorrect) {
			Predicate recipientEqualCondition = cb.equal(from.get(Package_.recipient), filter.getRecipint());
			Predicate betweenDate = null;
			if ((sDate) && (eDate)) {
				betweenDate = cb.between(from.get(Package_.date), filter.getStartDate(), filter.getEndDate());
			}
			if ((!sDate) && (eDate)) {
				startDate = new Date(0, 0, 0);
				endDate = filter.getEndDate();
				betweenDate = cb.between(from.get(Package_.date), startDate, endDate);
			}
			if ((sDate) && (!eDate)) {
				endDate = new Date();
				startDate = filter.getStartDate();
				betweenDate = cb.between(from.get(Package_.date), startDate, endDate);
			}
			cq.where(cb.and(recipientEqualCondition, betweenDate));
		}
		TypedQuery<Package> q = em.createQuery(cq);
		return q.getResultList();
	}
}
