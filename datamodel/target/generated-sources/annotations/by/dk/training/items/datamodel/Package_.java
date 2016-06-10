package by.dk.training.items.datamodel;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Package.class)
public abstract class Package_ {

	public static volatile SingularAttribute<Package, Date> date;
	public static volatile SingularAttribute<Package, Double> weight;
	public static volatile SingularAttribute<Package, String> description;
	public static volatile SingularAttribute<Package, BigDecimal> tax;
	public static volatile SingularAttribute<Package, String> countrySender;
	public static volatile SingularAttribute<Package, Recipient> idRecipient;
	public static volatile ListAttribute<Package, Product> products;
	public static volatile SingularAttribute<Package, UserProfile> idUser;
	public static volatile SingularAttribute<Package, BigDecimal> fine;
	public static volatile SingularAttribute<Package, BigDecimal> price;
	public static volatile SingularAttribute<Package, Boolean> paid;
	public static volatile SingularAttribute<Package, String> paymentDeadline;
	public static volatile SingularAttribute<Package, Long> id;
	public static volatile SingularAttribute<Package, BigDecimal> percentFine;

}

