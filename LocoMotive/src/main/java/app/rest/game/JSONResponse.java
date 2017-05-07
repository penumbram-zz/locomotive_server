package app.rest.game;

import app.rest.user.User;
import lombok.Getter;

/**
 * Created by tolgacaner on 06/05/2017.
 */
public class JSONResponse {
    @Getter
    private Integer code;
    @Getter
    private String message;

    public JSONResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
