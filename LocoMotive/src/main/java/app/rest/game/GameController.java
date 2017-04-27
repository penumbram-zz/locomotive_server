package app.rest.game;

import app.rest.user.JSONGameLobby;
import app.rest.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by tolgacaner on 21/04/2017.
 */
@CrossOrigin
@RestController
@RequestMapping("/game")
public class GameController {

    private IGameService gameService;

    @Autowired
    public GameController(IGameService gameService) {
        this.gameService = gameService;
    }

    @ResponseStatus(value = HttpStatus.OK,reason = "Created game succesfully")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createGame(@RequestBody Game game) {
        gameService.saveGame(game);
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Joined game successfully.")
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public void joinGame(@RequestBody JSONGameLobby jsonGameLobby) {
        Game game = gameService.findGameById(jsonGameLobby.getGameId());
        game.getPlayers().add(jsonGameLobby.getUser());
        gameService.saveGame(game);
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Left game successfully.")
    @RequestMapping(value = "/leave", method = RequestMethod.POST)
    public void leaveGame(@RequestBody JSONGameLobby jsonJoinGame) {
        Game game = gameService.findGameById(jsonJoinGame.getGameId());
        List<User> players = game.getPlayers();
        players.remove(jsonJoinGame.getUser());
        if (jsonJoinGame.getUser().getId() == game.getHostId()) { // host left the game
            if (players.isEmpty()) { //no one left in the lobby
                gameService.deleteById(game.getId());
            } else { //there are others
                game.setHostId(players.get(0).getId());
            }
            // assign a new host
        }
        gameService.saveGame(game);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public Iterable<Game> getGames() {
        return gameService.getGames();
    }


    @ResponseStatus(value = HttpStatus.OK, reason = "Game starting...")
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public void startGame(@RequestBody JSONGameLobby jsonGameLobby) {
        Game game = gameService.findGameById(jsonGameLobby.getGameId());
        game.getPlayers().add(jsonGameLobby.getUser());
        gameService.saveGame(game);
    }
/*
    private String fromUserRestController(String endpoint) {
        return "http://localhost:8080/user".concat(endpoint);
    }
*/

}
