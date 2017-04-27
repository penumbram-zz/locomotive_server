package app.rest.user;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by tolgacaner on 21/04/2017.
 */
@Data
public class JSONGameLobby {

    @NotBlank
    private Integer gameId;
    @NotBlank
    private User user;

}
