package app.rest.game;

import app.rest.user.JSONGameLobby;
import app.rest.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @ResponseStatus(value = HttpStatus.OK,reason = "Game created succesfully")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createGame(@RequestBody Game game) {
        gameService.saveGame(game);
        /*
        RestTemplate restTemplate = new RestTemplate();
        String getUserUrl
                = fromUserRestController("/id").concat("/" + game.getHostId());
        User user = restTemplate.getForEntity(getUserUrl, User.class).getBody();


        HttpHeaders responseHeaders = new HttpHeaders();


        if (user != null) { //host exists
            gameService.saveGame(game);
            return new ResponseEntity<>(user, responseHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Wrong Credentials", responseHeaders, HttpStatus.OK);
        }
        */
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Game joined successfully.")
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public void joinGame(@RequestBody JSONGameLobby jsonGameLobby) {
        Game game = gameService.findGameById(jsonGameLobby.getGameId());
        game.getPlayers().add(jsonGameLobby.getUser());
        gameService.saveGame(game);
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Game joined successfully.")
    @RequestMapping(value = "/leave", method = RequestMethod.POST)
    public void leaveGame(@RequestBody JSONGameLobby jsonJoinGame) {
        Game game = gameService.findGameById(jsonJoinGame.getGameId());
        game.getPlayers().remove(jsonJoinGame.getUser());
        gameService.saveGame(game);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public Iterable<Game> getAllAnnotations() {
        return gameService.getGames();
    }

    private String fromUserRestController(String endpoint) {
        return "http://localhost:8080/user".concat(endpoint);
    }


}
