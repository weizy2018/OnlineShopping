package entities;

import entities.Customer;
import entities.Product;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-13T15:27:18")
@StaticMetamodel(Orders.class)
public class Orders_ { 

    public static volatile SingularAttribute<Orders, Integer> amount;
    public static volatile SingularAttribute<Orders, Date> createTime;
    public static volatile SingularAttribute<Orders, String> phone;
    public static volatile SingularAttribute<Orders, Customer> customerId;
    public static volatile SingularAttribute<Orders, String> address;
    public static volatile SingularAttribute<Orders, String> state;
    public static volatile SingularAttribute<Orders, Integer> number;
    public static volatile SingularAttribute<Orders, Product> productId;

}