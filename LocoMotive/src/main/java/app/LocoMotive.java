package app;

import app.rest.socket.GameServer;
import org.java_websocket.WebSocketImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tolgacaner on 20/04/2017.
 */

@SpringBootApplication
@EnableAsync
public class LocoMotive {
    public static void main(String[] args) {
        SpringApplication.run(LocoMotive.class, args); //NOSONAR
        System.out.println("run");

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebSocketImpl.DEBUG = true;
                int port = 8884; // 843 flash policy port
                try {
                    GameServer s = new GameServer(port);
                    s.startServer();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        */



    }
}

