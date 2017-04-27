package app.rest.user;

/**
 * Created by tolgacaner on 20/04/2017.
 */

public interface IUserService {

    void saveUser(User user);
    void deleteUser(User user);
    void deleteById(long id);
    User getUserById(long id);
}
