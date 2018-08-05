package by.dk.training.items.datamodel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "package")
public class Package extends EntityItem {

	private static final long serialVersionUID = -8196441922163357291L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "recipient", nullable = false)
	private Recipient recipient;

	@Column(nullable = false)
	private BigDecimal price = new BigDecimal("0");

	@Column(nullable = false)
	private Double weight = new Double(0.0);

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUser")
	private UserProfile idUser;

	@Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;

	@Column(length = 1000)
	private String description;

	@Column(name = "country_sender", nullable = false, length = 100)
	private String countrySender;

	@Column(name = "payment_deadline", nullable = false)
	private String paymentDeadline;

	@Column(nullable = false)
	private BigDecimal penalty = new BigDecimal("0");

	@Column(nullable = false)
	private Boolean paid;

	@Column(nullable = false)
	private BigDecimal tax = new BigDecimal("0");

	@JoinTable(name = "package_2_product", joinColumns = { @JoinColumn(name = "package_id") }, inverseJoinColumns = {
			@JoinColumn(name = "product_id") })
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Product> products = new ArrayList<>();

	@Column(name = "percent_penalty")
	private BigDecimal percentPenalty;

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public UserProfile getIdUser() {
		return idUser;
	}

	public void setIdUser(UserProfile idUser) {
		this.idUser = idUser;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountrySender() {
		return countrySender;
	}

	public void setCountrySender(String countrySender) {
		this.countrySender = countrySender;
	}

	public String getPaymentDeadline() {
		return paymentDeadline;
	}

	public void setPaymentDeadline(String paymentDeadline) {
		this.paymentDeadline = paymentDeadline;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public BigDecimal getPercentPenalty() {
		return percentPenalty;
	}

	public void setPercentPenalty(BigDecimal percentPenalty) {
		this.percentPenalty = percentPenalty;
	}

	@Override
	public String toString() {
		return "Packages [trackingCode=" + getId() + ", price=" + price + ", weight=" + weight + ", date=" + date
				+ ", description=" + description + ", countrySender=" + countrySender + ", paymentDeadline="
				+ paymentDeadline + ", penalty=" + penalty + ", paid=" + paid + "]";
	}

}
