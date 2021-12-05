package ticketservice.model;
import java.util.Objects;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "movie")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String genre;
    private int length;

    @Override
    public String toString() {
        return title + " (" + genre + ", " + length + " minutes)";
    }
}