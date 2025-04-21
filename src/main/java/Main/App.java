package Main;

import network.Server;

import java.io.IOException;

public class App {
    public static void main(String[] args)
    {
        try {
            Server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
