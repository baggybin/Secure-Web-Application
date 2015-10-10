package entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-26T14:22:45")
@StaticMetamodel(Addlog.class)
public class Addlog_ { 

    public static volatile SingularAttribute<Addlog, Integer> amount;
    public static volatile SingularAttribute<Addlog, BigDecimal> price;
    public static volatile SingularAttribute<Addlog, Date> lastUpdate;
    public static volatile SingularAttribute<Addlog, String> name;
    public static volatile SingularAttribute<Addlog, String> description;
    public static volatile SingularAttribute<Addlog, Integer> id;

}