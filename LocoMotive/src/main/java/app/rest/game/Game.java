package app.rest.game;

import app.rest.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

/**
 * Created by tolgacaner on 21/04/2017.
 */
@Data
@Entity
public class Game {

    @Id
    @GeneratedValue
    private long id; // Database primary key

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "host_id")
    Integer hostId;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<User> players;

/*
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @Getter @Setter
    User host;
*/
}
