package multiplayer;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

class Client extends Thread {
    private Socket socket;
    boolean polaczony = false;
    BufferedReader wejscie;
    String kolejnoscKart;
    @Override
    public void run() {
        try {
            socket = new Socket("localhost", 2345);
            wejscie = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            polaczony = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wejscie = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            kolejnoscKart = wejscie.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
            Board gameBoard = new Board(kolejnoscKart);
            gameBoard.setPreferredSize(new Dimension(500, 500)); //need to use this instead of setSize
            gameBoard.setLocation(500, 250);
            gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameBoard.pack();
            gameBoard.setVisible(true);
            System.out.println(socket.isConnected());
    }
}

public class MemoryClient extends Thread {
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}