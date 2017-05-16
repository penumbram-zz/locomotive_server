package app.rest.game;

import app.rest.user.JSONGameLobby;
import app.rest.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tolgacaner on 06/05/2017.
 */

@Data
public class JSONStartGame extends JSONGameLobby {}
