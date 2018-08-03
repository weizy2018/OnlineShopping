package entities;

import entities.Customer;
import entities.Product;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-13T15:27:18")
@StaticMetamodel(Store.class)
public class Store_ { 

    public static volatile SingularAttribute<Store, BigDecimal> income;
    public static volatile SingularAttribute<Store, String> name;
    public static volatile SingularAttribute<Store, Date> creationTime;
    public static volatile CollectionAttribute<Store, Product> productCollection;
    public static volatile SingularAttribute<Store, Customer> customer;
    public static volatile SingularAttribute<Store, String> storeId;
    public static volatile SingularAttribute<Store, String> introduction;

}