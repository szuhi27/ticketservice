package ticketservice.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "screening")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Screening{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String movie;
    private String room;
    private LocalDateTime date;

    //TODO
    @Override
    public String toString() {
        return movie+", screened in room "+room+ ", at "
        + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}
