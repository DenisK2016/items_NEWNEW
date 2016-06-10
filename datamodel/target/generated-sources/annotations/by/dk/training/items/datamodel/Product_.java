package by.dk.training.items.datamodel;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SingularAttribute<Product, UserProfile> idUser;
	public static volatile ListAttribute<Product, Type> types;
	public static volatile SingularAttribute<Product, BigDecimal> priceProduct;
	public static volatile SingularAttribute<Product, Double> weight;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile SingularAttribute<Product, String> nameProduct;
	public static volatile SingularAttribute<Product, Boolean> status;

}

