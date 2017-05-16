package app.rest.socket;

import app.rest.game.DistanceCalculator;
import app.rest.game.Game;
import app.rest.game.Prize;
import com.sun.mail.imap.Utility;
import lombok.Setter;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.scheduling.annotation.Async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static app.rest.util.Utility.stringToJSONObject;
import static app.rest.util.Utility.JSONObjectToString;

/**
 * Created by tolgacaner on 28/04/2017.
 */
public class GameServer extends WebSocketServer {


    private static final Double PRIZE_RADIUS = 5.0;
    List players = new ArrayList();
    public GameServerDelegate gameServerDelegate;
    @Setter
    Game game;

    public GameServer( int port ) throws UnknownHostException {
        super( new InetSocketAddress(port) );
    }

    public GameServer( InetSocketAddress address ) {
        super( address );
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        this.sendToAll( "new connection: " + clientHandshake.getResourceDescriptor());
        players.add(new Object());
        System.out.println("onOpen");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        this.sendToAll( webSocket + " has left the room!" );
        System.out.println("onClose");
        players.remove(0);
        if (players.size() == 0) {
            gameServerDelegate.serverFreed(this);
        }
    }

    @Override
    public void onWebsocketCloseInitiated(WebSocket webSocket, int i, String s) {
        super.onWebsocketCloseInitiated(webSocket, i, s);
        System.out.println("onWebsocketCloseInitiated");
    }

    @Override
    public void onWebsocketClosing(WebSocket webSocket, int i, String s, boolean b) {
        super.onWebsocketClosing(webSocket, i, s, b);
        System.out.println("onWebsocketClosing");
    }

    @Override
    public void onCloseInitiated(WebSocket webSocket, int i, String s) {
        super.onCloseInitiated(webSocket, i, s);
        System.out.println("onCloseInitiated");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        Message message = stringToJSONObject(s,Message.class);
        boolean pointsGained = checkIfPointGained(message);
        message.setPrizes(game.getPrizes());
        String messageNew = JSONObjectToString(message);
        this.sendToAll(messageNew);
        if (pointsGained) {
            gameServerDelegate.updatePrizes(game.getPrizes(),game.getId());
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        e.printStackTrace();
        System.out.println("onError");
    }

    @Override
    public void onStart() {
        System.out.println("onStart");
    }

    @Async
    public void startServer() throws InterruptedException , IOException {
        start();
        System.out.println( "Game Server started on port: " + getPort() );

        Thread serverThread = new Thread(() -> {
            BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
            while (true) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    System.out.println("CANT SLEEP");
                    e.printStackTrace();
                }
                String in = null;
                try {
                    while (sysin.ready()) {
                        in = sysin.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (in != null) {
                    sendToAll( in );
                    if( in.equals("exit")) {
                        try {
                            stop();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        });
        serverThread.start();
    }

    /**
     * Sends <var>text</var> to all currently connected WebSocket clients.
     *
     * @param text
     *            The String to send across the network.
     * @throws InterruptedException
     *             When socket related I/O errors occur.
     */
    public void sendToAll( String text ) {
        Collection<WebSocket> con = connections();
        synchronized ( con ) {
            for( WebSocket c : con ) {
                c.send( text );
            }
        }
    }



    //MARK: checkIfPointGain

    private boolean checkIfPointGained(Message message) {
        boolean pointsGained = false;
        synchronized (GameServer.class) {
            for (Prize prize : game.getPrizes()) {
                if (prize.getClaimer() == -1) { //nobody claimed it
                    Double distance = DistanceCalculator.distance(prize.getLatitude(), prize.getLongitude(), message.getPosition().getLatitude(), message.getPosition().getLongitude());
                    if (distance < PRIZE_RADIUS) {
                        prize.setClaimer(message.getId());
                        pointsGained = true;
                    }
                }
            }
        }
        return pointsGained;
    }


}
