package app.rest.socket;

import lombok.Data;

/**
 * Created by tolgacaner on 27/04/2017.
 */

@Data
public class DummyMessage {
    private String name;

    public DummyMessage() {}
    public DummyMessage(String name) {
        this.name = name;
    }
}
