package app.rest.user;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tolgacaner on 20/04/2017.
 */

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Getter
    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JSONResponseAddUser addUser(@RequestBody User user) {
        userService.saveUser(user);
        return new JSONResponseAddUser(user,200,"User added successfully");
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "User deleted successfully")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Transactional
    public void deleteUser(@RequestBody User user) {
        userService.deleteById(user.getId());
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @Transactional
    public User getUserById(@PathVariable(value="id") long id) {
        return userService.getUserById(id);
    }

}
