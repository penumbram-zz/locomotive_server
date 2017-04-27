package app.rest.socket;

import lombok.Getter;

/**
 * Created by tolgacaner on 27/04/2017.
 */
public class Greeting {

    @Getter
    private String content;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }


}
