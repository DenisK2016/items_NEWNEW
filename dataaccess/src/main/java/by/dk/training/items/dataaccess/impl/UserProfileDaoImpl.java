package by.dk.training.items.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.dk.training.items.dataaccess.UserProfileDao;
import by.dk.training.items.dataaccess.filters.UserFilter;
import by.dk.training.items.datamodel.UserCredentials_;
import by.dk.training.items.datamodel.UserProfile;
import by.dk.training.items.datamodel.UserProfile_;

@Repository
public class UserProfileDaoImpl extends AbstractDaoImpl<UserProfile, Long> implements UserProfileDao {

	protected UserProfileDaoImpl() {
		super(UserProfile.class);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserProfile> find(UserFilter filter) {
		EntityManager em = getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<UserProfile> cq = cb.createQuery(UserProfile.class);

		Root<UserProfile> from = cq.from(UserProfile.class); // SELECT .. FROM
																// ...

		cq.select(from); // Указывает что селектать SELECT *. from - это
							// таблица,
							// а from.get... - это конкретная колонка

		boolean fName = (filter.getFirstName() != null);
		boolean lName = (filter.getLastName() != null);
		boolean login = (filter.getLogin() != null);
		boolean create = (filter.getCreated() != null);
		boolean stat = (filter.getStatus() != null);
		boolean post = (filter.getPost() != null);
		boolean rank = (filter.getRank() != null);
		boolean email = (filter.getEmail() != null);
		boolean id = (filter.getId() != null);
		boolean password = filter.getPassword() != null;
		boolean filt = (fName || lName || login || create || stat || post || rank || email || id || password);

		if (filt) {
			Predicate idEqualCondition = cb.equal(from.get(UserProfile_.id), filter.getId());
			Predicate loginEqualCondition = cb.equal(from.get(UserProfile_.login), filter.getLogin());
			Predicate passwordEqualCondition = cb.equal(from.get(UserProfile_.password), filter.getPassword());
			Predicate fNameEqualCondition = cb.equal(
					from.get(UserProfile_.userCredentials).get(UserCredentials_.firstName), filter.getFirstName());
			Predicate lNameEqualCondition = cb
					.equal(from.get(UserProfile_.userCredentials).get(UserCredentials_.lastName), filter.getLastName());
			Predicate createdEqualCondition = cb
					.equal(from.get(UserProfile_.userCredentials).get(UserCredentials_.created), filter.getCreated());
			Predicate statusEqualCondition = cb
					.equal(from.get(UserProfile_.userCredentials).get(UserCredentials_.status), filter.getStatus());
			Predicate postEqualCondition = cb.equal(from.get(UserProfile_.userCredentials).get(UserCredentials_.post),
					filter.getPost());
			Predicate rankEqualCondition = cb.equal(from.get(UserProfile_.userCredentials).get(UserCredentials_.rank),
					filter.getRank());
			Predicate emailEqualCondition = cb.equal(from.get(UserProfile_.userCredentials).get(UserCredentials_.email),
					filter.getEmail());
			cq.where(cb.or(loginEqualCondition, fNameEqualCondition, lNameEqualCondition, createdEqualCondition,
					statusEqualCondition, postEqualCondition, rankEqualCondition, emailEqualCondition, idEqualCondition,
					passwordEqualCondition));
		}

		// set fetching
		if (filter.isFetchCredentials()) {
			from.fetch(UserProfile_.userCredentials, JoinType.LEFT);
		}

		if (filter.isFetchPackages()) {
			from.fetch(UserProfile_.packages, JoinType.LEFT);
		}

		// set sort params
		if (filter.getSortProperty() != null) {
			boolean log = filter.getSortProperty() == UserProfile_.login;
			boolean idn = filter.getSortProperty() == UserProfile_.id;
			boolean pass = filter.getSortProperty() == UserProfile_.password;
			if (log || idn || pass) {
				cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
			} else {
				cq.orderBy(new OrderImpl(from.get(UserProfile_.userCredentials).get(filter.getSortProperty()),
						filter.isSortOrder()));
			}
		}

		TypedQuery<UserProfile> q = em.createQuery(cq);

		// set paging
		if (filter.getOffset() != null && filter.getLimit() != null) {
			q.setFirstResult(filter.getOffset());
			q.setMaxResults(filter.getLimit());
		}

		// set execute query
		List<UserProfile> allitems = q.getResultList();

		return allitems;
	}

	@Override
	public Long count(UserFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<UserProfile> from = cq.from(UserProfile.class);
		cq.select(cb.count(from));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	protected void setPaging(UserFilter filter, TypedQuery<UserFilter> q) {
		if (filter.getOffset() != null && filter.getLimit() != null) {
			q.setFirstResult(filter.getOffset());
			q.setMaxResults(filter.getLimit());
		}
	}

	@Override
	public UserProfile find(String login, String password) {

		EntityManager em = getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<UserProfile> cq = cb.createQuery(UserProfile.class);

		Root<UserProfile> from = cq.from(UserProfile.class);

		cq.select(from);
		Predicate usernameEqualCondition = cb.equal(from.get(UserProfile_.login), login);
		Predicate passwEqualCondition = cb.equal(from.get(UserProfile_.password), password);
		cq.where(cb.and(usernameEqualCondition, passwEqualCondition));

		TypedQuery<UserProfile> q = em.createQuery(cq);

		List<UserProfile> allitems = q.getResultList();

		if (allitems.isEmpty()) {
			return null;
		} else if (allitems.size() == 1) {
			return allitems.get(0);
		} else {
			throw new IllegalArgumentException("==more than 1 user found==");
		}
	}

}
