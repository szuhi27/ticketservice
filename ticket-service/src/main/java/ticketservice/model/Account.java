package ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;



@Entity
@Table(name = "account")
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;
    private String passw;
    private AccountRole accountRole;

    public Account(String username, String passw,AccountRole role) {
        this.username = username;
        this.passw = passw;
        this.accountRole = role;
    }

    /*@Override
    public int hashCode() {
        return Objects.hash(username, passw);
    }*/
}