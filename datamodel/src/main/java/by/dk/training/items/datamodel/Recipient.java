package by.dk.training.items.datamodel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "recipient")
public class Recipient implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private Long id;
	@Column(nullable = false, length = 100)
	private String name;
	@Column(nullable = false, length = 100)
	private String city;
	@Column(nullable = false, length = 100)
	private String address;
	@OneToMany(mappedBy = "idRecipient", fetch = FetchType.LAZY)
	private Set<Package> packages = new HashSet<>();
	@ManyToOne(targetEntity = UserProfile.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	private UserProfile idUser;
	@Column(name = "counter_packages")
	private Integer counterPackages = new Integer(0);

	public Integer getCounterPackages() {
		return counterPackages;
	}

	public void setCounterPackages(Integer counterPackages) {
		this.counterPackages = this.counterPackages + counterPackages;
	}

	public Recipient() {
		super();
	}

	public UserProfile getIdUser() {
		return idUser;
	}

	public void setIdUser(UserProfile idUser) {
		this.idUser = idUser;
	}

	public Set<Package> getPackages() {
		return packages;
	}

	public void setPackages(Package pack) {
		this.packages.add(pack);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Recipient [id=" + id + ", name=" + name + ", city=" + city + ", address=" + address + ", packages="
				+ packages + "]";
	}

}
