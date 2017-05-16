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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

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
        game.setPrizes(GameMapGenerator.getPrizes(game));
        gameService.saveGame(game);
        return new JSONResponseGame(game,200,"Created game succesfully");
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Joined game successfully")
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public void joinGame(@RequestBody JSONGameLobby jsonGameLobby) {
        System.out.println("JOINING GAME!!");
        Game game = gameService.findGameById(jsonGameLobby.getGameId());
        if (game == null) {
            System.out.println("Game is null");
        } else {
            System.out.println("Game is not null");
        }
        if (game.getNumberOfPlayers() <= game.getPlayers().size()) {
            System.out.println("returning");
            return;
        }
        System.out.println("Game 123");
        game.getPlayers().add(jsonGameLobby.getUser());
        System.out.println("Game 124");
        gameService.saveGame(game);
        System.out.println("Game 125");
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
        int port = startWebSocket(game);
        game.setStarted(true);
        if (port == -1) {
            System.out.println("NO AVAILABLE PORTS RIGHT NOW! SERVER BUSY");
        }
        game.setPort(port);
        System.out.println("startGame 6");
        gameService.saveGame(game);
        System.out.println("startGame 7");
        return new JSONResponseGame(game,200,"Game starting");
    }

    private int startWebSocket(Game game) {
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
        s.setGame(game);
        return port;
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public JSONResponseGame checkStatus(@RequestBody JSONGameLobby jsonGameLobby) {
        Game game = gameService.findGameById(jsonGameLobby.getGameId());
        return new JSONResponseGame(game,200,"Status Update");
    }

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public Iterable<Score> getResults(@RequestBody JSONGameLobby jsonJoinGame) {
        Game game = gameService.findGameById(jsonJoinGame.getGameId());
        List<Prize> prizes = game.getPrizes();
        Map<Integer, Integer> scoresMap = new HashMap<Integer,Integer>();
        initializeResultsMap(scoresMap,game.getPlayers());
        for (Prize p : prizes) {
            Integer s = scoresMap.get((int)p.getClaimer());
            scoresMap.put((int) p.getClaimer(), s + p.getPoints());
        }
        List<Score> results = new ArrayList<Score>();
        for (Integer id : scoresMap.keySet()) {
            Score score = new Score();
            Predicate<User> predicate = c-> c.getId() == id.longValue();
            User  user = game.getPlayers().stream().filter(predicate).findFirst().get();
            score.setUser(user);
            score.setPoints(scoresMap.get(id));
            results.add(score);
        }
        return results;
    }


    @Override
    public void serverFreed(GameServer gameServer) {
        unusedServers.add(gameServer);
    }

    @Override
    public void updatePrizes(List<Prize> prizes,long gameId) {
        Game game = gameService.findGameById(gameId);
        game.setPrizes(prizes);
        gameService.saveGame(game);
    }


    private void initializeResultsMap(Map<Integer,Integer> map,List<User> players) {
        for (User user : players) {
            map.put((int) user.getId(), 0);
        }
    }

}
