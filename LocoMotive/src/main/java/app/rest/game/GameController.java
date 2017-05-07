package app.rest.game;

import app.rest.socket.GameServer;
import app.rest.socket.GameServerDelegate;
import app.rest.user.JSONGameLobby;
import app.rest.user.User;
import org.java_websocket.WebSocketImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tolgacaner on 21/04/2017.
 */
@CrossOrigin
@RestController
@RequestMapping("/game")
public class GameController implements GameServerDelegate {

    private IGameService gameService;

    private List<Integer> availabePorts = new ArrayList<>();
    private List<GameServer> unusedServers = new ArrayList<>();

    @Autowired
    public GameController(IGameService gameService) {
        this.gameService = gameService;
        for (int i = 8884; i < 8888; i++) {
            availabePorts.add(i);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JSONResponseGame createGame(@RequestBody Game game) {
        gameService.saveGame(game);
        return new JSONResponseGame(game,200,"Created game succesfully");
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Joined game successfully")
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public void joinGame(@RequestBody JSONGameLobby jsonGameLobby) {
        Game game = gameService.findGameById(jsonGameLobby.getGameId());
        game.getPlayers().add(jsonGameLobby.getUser());
        gameService.saveGame(game);
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Left game successfully")
    @RequestMapping(value = "/leave", method = RequestMethod.POST)
    public void leaveGame(@RequestBody JSONGameLobby jsonJoinGame) {
        Game game = gameService.findGameById(jsonJoinGame.getGameId());
        List<User> players = game.getPlayers();
        players.remove(jsonJoinGame.getUser());
        if (jsonJoinGame.getUser().getId() == game.getHostId()) { // host left the game
            if (players.isEmpty()) { //no one left in the lobby
                gameService.deleteById(game.getId()); //delete the game and quit
                return;
            } else { //there are others
                game.setHostId(players.get(0).getId()); // assign a new host
            }
        }
        gameService.saveGame(game);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public Iterable<Game> getGames() {
        return gameService.getGames();
    }


    @ResponseStatus(value = HttpStatus.OK, reason = "")
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public JSONResponseGame startGame(@RequestBody JSONStartGame jsonStartGame) {
        Game game = gameService.findGameById(jsonStartGame.getGameId());
        game.setStartTime(jsonStartGame.getStartDate());
        int port = startWebSocket();
        if (port == -1) {
            System.out.println("NO AVAILABLE PORTS RIGHT NOW! SERVER BUSY");
        }
        game.setPort(port);
        gameService.saveGame(game);
        return new JSONResponseGame(game,200,"Game starting");
    }

    private int startWebSocket() {
        int port = -1;
        GameServer s = null;
        if (!unusedServers.isEmpty()) {
            s = unusedServers.get(0);
            unusedServers.remove(0);
            port = s.getPort();
        } else if (!availabePorts.isEmpty()) { //8887; // 843 flash policy port
            port = availabePorts.get(0);
            availabePorts.remove(0);
            try {
                s = new GameServer(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                s.gameServerDelegate = this;
                s.startServer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return port;
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public JSONResponseGame checkStatus(@RequestBody JSONGameLobby jsonGameLobby) {
        Game game = gameService.findGameById(jsonGameLobby.getGameId());
        return new JSONResponseGame(game,200,"Status Update");
    }

    @Override
    public void serverFreed(GameServer gameServer) {
        unusedServers.add(gameServer);
    }
/*
    private String fromUserRestController(String endpoint) {
        return "http://localhost:8080/user".concat(endpoint);
    }
*/

}
