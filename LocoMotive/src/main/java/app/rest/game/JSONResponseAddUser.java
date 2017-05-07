package app.rest.game;

import app.rest.user.User;
import lombok.Getter;

/**
 * Created by tolgacaner on 20/04/2017.
 */
public class JSONResponseAddUser extends JSONResponse {

    @Getter
    private User user;

    public JSONResponseAddUser(User user, Integer code, String message) {
        super(code,message);
        this.user = user;
    }
}
