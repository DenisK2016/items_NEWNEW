package by.dk.training.items.dataaccess.filters;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;

import by.dk.training.items.datamodel.Ranks;
import by.dk.training.items.datamodel.StatusUser;

public class UserFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private Date created;
	private StatusUser status;
	private String post;
	private Ranks rank;
	private String email;
	private String login;
	private String password;
	@SuppressWarnings("rawtypes")
	private SingularAttribute sortProperty;
	private boolean sortOrder;
	private Integer offset;
	private Integer limit;

	private boolean isFetchCredentials;
	private boolean isFetchPackages;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public StatusUser getStatus() {
		return status;
	}

	public void setStatus(StatusUser status) {
		this.status = status;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Ranks getRank() {
		return rank;
	}

	public void setRank(Ranks rank) {
		this.rank = rank;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@SuppressWarnings("rawtypes")
	public SingularAttribute getSortProperty() {
		return sortProperty;
	}

	public void setSortProperty(@SuppressWarnings("rawtypes") SingularAttribute sortProperty) {
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

	public boolean isFetchCredentials() {
		return isFetchCredentials;
	}

	public void setFetchCredentials(boolean isFetchCredentials) {
		this.isFetchCredentials = isFetchCredentials;
	}

	public boolean isFetchPackages() {
		return isFetchPackages;
	}

	public void setFetchPackages(boolean isFetchPackages) {
		this.isFetchPackages = isFetchPackages;
	}
}
