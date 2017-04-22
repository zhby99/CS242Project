package Network;


import Game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by boyinzhang on 4/22/17.
 */
public class Server {

    /**
     * Runs the application. Pairs up clients that connect.
     */
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(8901);
        System.out.println("Splendor Server is Running");

        try {
            while (true){
                Game game = new Game(listener.accept());
            }
        } finally {
            listener.close();
        }
    }
}
