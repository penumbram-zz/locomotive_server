package app.rest.game;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tolgacaner on 21/04/2017.
 */

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
}
