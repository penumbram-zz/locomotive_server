package app.rest.game;

import app.rest.user.User;
import lombok.Getter;

/**
 * Created by tolgacaner on 20/04/2017.
 */
public class JSONResponseAddUser {

    @Getter
    private User user;
    @Getter
    private Integer code;
    @Getter
    private String message;

    public JSONResponseAddUser(User user, Integer code, String message) {
        this.user = user;
        this.code = code;
        this.message = message;
    }
}
