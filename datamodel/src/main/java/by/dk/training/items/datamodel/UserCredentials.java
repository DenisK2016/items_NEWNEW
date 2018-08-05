package by.dk.training.items.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user_credentials")
public class UserCredentials extends EntityItem {

	private static final long serialVersionUID = -3808406645617943068L;

	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	@Column
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date created;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusUser status;

	@Column(length = 200)
	private String post;

	@Column
	@Enumerated(EnumType.STRING)
	private Ranks rank;

	@Column(nullable = false, unique = true, updatable = false)
	private String email;

	@OneToOne(mappedBy = "userCredentials")
	private UserProfile user;

	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	public UserCredentials() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Ranks getRank() {
		return rank;
	}

	public void setRank(Ranks rank) {
		this.rank = rank;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "UserCredentials [id=" + getId() + ", firstName=" + firstName + ", lastName=" + lastName + ", created="
				+ created + ", status=" + status + ", post=" + post + ", rank=" + rank + ", email=" + email
				+ ", userProfile=" + user + "]";
	}

}
