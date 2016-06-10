package by.dk.training.items.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Type.class)
public abstract class Type_ {

	public static volatile ListAttribute<Type, Type> childTypes;
	public static volatile SingularAttribute<Type, UserProfile> idUser;
	public static volatile SingularAttribute<Type, String> typeName;
	public static volatile SingularAttribute<Type, Long> id;
	public static volatile SingularAttribute<Type, Type> parentType;

}

