package by.dk.training.items.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Recipient.class)
public abstract class Recipient_ {

	public static volatile SingularAttribute<Recipient, UserProfile> idUser;
	public static volatile SingularAttribute<Recipient, Integer> counterPackages;
	public static volatile SingularAttribute<Recipient, String> address;
	public static volatile SingularAttribute<Recipient, String> city;
	public static volatile SingularAttribute<Recipient, String> name;
	public static volatile SingularAttribute<Recipient, Long> id;
	public static volatile SetAttribute<Recipient, Package> packages;

}

