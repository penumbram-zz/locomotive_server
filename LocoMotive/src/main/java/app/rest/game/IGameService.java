package app.rest.game;

/**
 * Created by tolgacaner on 21/04/2017.
 */
public interface IGameService {

    void saveGame(Game game);
    Game findGameById(long id);
    Iterable<Game> getGames();
    void deleteById(long id);

}
