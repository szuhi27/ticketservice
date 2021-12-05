package ticketservice.model;

import javax.persistence.*;
import java.util.Objects;



@Entity
@Table(name= "account")
public class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;
    private String passw;

    public Account(String username, String passw){
        this.username = username;
        this.passw = passw;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account that = (Account) o;
        return Double.compare(that.netPrice, netPrice) == 0 &&
        Objects.equals(name, that.name);
    }*/

    @Override
    public int hashCode() {
        return Objects.hash(username, passw);
    }
}