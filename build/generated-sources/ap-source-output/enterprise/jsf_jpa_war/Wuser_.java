package enterprise.jsf_jpa_war;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-26T14:22:45")
@StaticMetamodel(Wuser.class)
public class Wuser_ { 

    public static volatile SingularAttribute<Wuser, String> password;
    public static volatile SingularAttribute<Wuser, Boolean> role;
    public static volatile SingularAttribute<Wuser, Integer> id;
    public static volatile SingularAttribute<Wuser, String> username;
    public static volatile SingularAttribute<Wuser, Date> since;

}