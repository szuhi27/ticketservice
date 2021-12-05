package ticketservice.model;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;


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

    @Override
    public int hashCode() {
        return Objects.hash(title, length, genre);
    }

    public Movie(String title, String genre, int length){
        this.title = title;
        this.genre = genre;
        this.length = length;
    }
}