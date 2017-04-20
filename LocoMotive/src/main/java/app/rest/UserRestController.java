package app.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tolgacaner on 20/04/2017.
 */

@RestController
@CrossOrigin
public class UserRestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testMethod() {
        return "test says Hello World!";
    }

}
