package by.dk.training.items.datamodel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "recipient")
public class Recipient extends EntityItem {

	private static final long serialVersionUID = -714929996431432981L;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 100)
	private String city;

	@Column(nullable = false, length = 100)
	private String address;

	@OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Package> packages = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUser")
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

	public void setPackages(Set<Package> packages) {
		this.packages = packages;
	}

	public Set<Package> getPackages() {
		return packages;
	}

	public void setPackages(Package pack) {
		this.packages.add(pack);
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
		return "Recipient [id=" + getId() + ", name=" + name + ", city=" + city + ", address=" + address + ", packages="
				+ packages + "]";
	}

}
