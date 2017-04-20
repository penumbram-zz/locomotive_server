package app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tolgacaner on 20/04/2017.
 */

@RestController
@CrossOrigin
public class UserController {

    private IUserService userService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testMethod() {
        return "test says Hello World!";
    }

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "User is saved successfully.")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addAnnotation(@RequestBody User user) {
        userService.saveUser(user);
    }

}
