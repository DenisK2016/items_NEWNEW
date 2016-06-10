package by.dk.training.items.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserProfile.class)
public abstract class UserProfile_ {

	public static volatile SingularAttribute<UserProfile, String> password;
	public static volatile SingularAttribute<UserProfile, UserCredentials> userCredentials;
	public static volatile SingularAttribute<UserProfile, Long> id;
	public static volatile SingularAttribute<UserProfile, String> login;
	public static volatile SetAttribute<UserProfile, Package> packages;

}

