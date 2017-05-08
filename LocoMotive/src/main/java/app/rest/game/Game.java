package app.rest.game;

import app.rest.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;
import java.util.Calendar;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "host_id") //timezone="GMT"
    long hostId;

    @Column(nullable = false)
    Integer numberOfPlayers;

    @Column(nullable = false)
    Double latitude;

    @Column(nullable = false)
    Double longitude;

    @Column(nullable = false)
    Double radius;

    @Column
    @OneToMany(cascade = CascadeType.MERGE)
    private List<User> players;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss")
    @Column
    Calendar startTime;

    @Column
    int port;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Prize> prizes;

/*
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @Getter @Setter
    User host;
*/
}
