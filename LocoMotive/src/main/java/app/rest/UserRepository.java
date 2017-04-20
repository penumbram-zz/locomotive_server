package app.rest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tolgacaner on 20/04/2017.
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
