package app.rest.socket;

import com.sun.mail.imap.Utility;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static app.rest.util.Utility.stringToJSONObject;

/**
 * Created by tolgacaner on 28/04/2017.
 */
public class GameServer extends WebSocketServer {

    List players = new ArrayList();
    public GameServerDelegate gameServerDelegate;

    public GameServer( int port ) throws UnknownHostException {
        super( new InetSocketAddress(port) );
    }

    public GameServer( InetSocketAddress address ) {
        super( address );
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        this.sendToAll( "new connection: " + clientHandshake.getResourceDescriptor() );
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
        this.sendToAll(s);
        Message message = stringToJSONObject(s,Message.class);
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


    public void startServer() throws InterruptedException , IOException {
        start();
        System.out.println( "ChatServer started on port: " + getPort() );

        Thread serverThread = new Thread(() -> {
            BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
            while ( true ) {
                String in = null;
                try {
                    in = sysin.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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


}
