package app.rest.game;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by tolgacaner on 07/05/2017.
 */
@Data
@Entity
public class Prize {

    @Id
    @GeneratedValue
    private long id; // Database primary key

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String color = "b2cd30"; //green

    @Column(nullable = false)
    private Integer points = 100;

    @Column
    private long claimer = -1;

    public Prize() {}

    public Prize(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
