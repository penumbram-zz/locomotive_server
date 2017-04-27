package app.rest.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by tolgacaner on 20/04/2017.
 */
@Data
@Entity
@TableGenerator(name="user",initialValue = 1)
public class User {


    @Id
    @GeneratedValue
    private long id; // Database primary key

    @Column(nullable = false) @Getter @Setter
    private String nickname;
}
