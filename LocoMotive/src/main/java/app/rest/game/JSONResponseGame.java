package app.rest.game;

import app.rest.user.User;
import lombok.Getter;

/**
 * Created by tolgacaner on 06/05/2017.
 */
public class JSONResponseGame extends JSONResponse {

    @Getter
    private Game game;


    public JSONResponseGame(Game game, Integer code, String message) {
        super(code,message);
        this.game = game;
    }
}
