package app.rest.game;

import app.rest.user.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by tolgacaner on 11/05/2017.
 */

@Data
public class Score {

    Integer points;
    User user;
}
