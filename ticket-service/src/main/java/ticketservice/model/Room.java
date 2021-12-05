package ticketservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "room")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private int rows;
    private int columns;

    @Override
    public String toString() {
        return "Room "+name + " with "+rows*columns+" seats, "+rows+" rows and "+
            columns+" columns.";
    }
}
