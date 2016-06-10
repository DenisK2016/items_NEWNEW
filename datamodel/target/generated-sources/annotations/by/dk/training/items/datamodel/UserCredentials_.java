package by.dk.training.items.datamodel;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserCredentials.class)
public abstract class UserCredentials_ {

	public static volatile SingularAttribute<UserCredentials, String> firstName;
	public static volatile SingularAttribute<UserCredentials, String> lastName;
	public static volatile SingularAttribute<UserCredentials, String> post;
	public static volatile SingularAttribute<UserCredentials, Date> created;
	public static volatile SingularAttribute<UserCredentials, Ranks> rank;
	public static volatile SingularAttribute<UserCredentials, Long> id;
	public static volatile SingularAttribute<UserCredentials, UserProfile> user;
	public static volatile SingularAttribute<UserCredentials, String> email;
	public static volatile SingularAttribute<UserCredentials, StatusUser> status;

}

