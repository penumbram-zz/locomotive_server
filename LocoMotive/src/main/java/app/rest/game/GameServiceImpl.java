package app.rest.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tolgacaner on 21/04/2017.
 */
@Service
public class GameServiceImpl implements IGameService {

    GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    @Override
    public Game findGameById(long id) {
        return gameRepository.findOne(id);
    }

    @Override
    public Iterable<Game> getGames() {
        return gameRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        gameRepository.delete(id);
    }

}
