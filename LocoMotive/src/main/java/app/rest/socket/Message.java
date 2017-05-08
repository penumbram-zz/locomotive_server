package app.rest.socket;

import app.rest.game.Prize;
import lombok.Data;

import java.util.List;

/**
 * Created by tolgacaner on 01/05/2017.
 */

@Data
public class Message {
    public Integer id;
    public String name;
    public Position position;
    public List<Prize> prizes;
}
