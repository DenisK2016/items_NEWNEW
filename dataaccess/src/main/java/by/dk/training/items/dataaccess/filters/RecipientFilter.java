package by.dk.training.items.dataaccess.filters;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import by.dk.training.items.datamodel.UserProfile;

public class RecipientFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String city;
	private String address;
	private UserProfile user;

	private SingularAttribute<Object, Object> sortProperty;
	private boolean sortOrder;
	private Integer offset;
	private Integer limit;
	private Integer counterPackages;

	private boolean isFetchPackages;
	private boolean isFetchUser;

	public Integer getCounterPackages() {
		return counterPackages;
	}

	public void setCounterPackages(Integer counterPackages) {
		this.counterPackages = counterPackages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	public boolean isFetchUser() {
		return isFetchUser;
	}

	public void setFetchUser(boolean isFetchUser) {
		this.isFetchUser = isFetchUser;
	}

	public boolean isFetchPackages() {
		return isFetchPackages;
	}

	public void setFetchPackages(boolean isFetchPackages) {
		this.isFetchPackages = isFetchPackages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public SingularAttribute<Object, Object> getSortProperty() {
		return sortProperty;
	}

	public void setSortProperty(SingularAttribute<Object, Object> sortProperty) {
		this.sortProperty = sortProperty;
	}

	public boolean isSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(boolean sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
