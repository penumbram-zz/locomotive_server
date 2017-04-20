package app.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by tolgacaner on 20/04/2017.
 */
@Data
@Entity
public class User {


    @Id
    @GeneratedValue
    @JsonIgnore
    private long id; // Database primary key

    @Column(nullable = false) @Getter @Setter
    private String nickname;
}
