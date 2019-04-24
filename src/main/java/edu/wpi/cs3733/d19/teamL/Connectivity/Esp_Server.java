package edu.wpi.cs3733.d19.teamL.Connectivity;
import edu.wpi.cs3733.d19.teamL.RoomBooking.*;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Esp_Server extends Thread {

    private static final int serverPort = 37899;

    private ServerSocket server;
    private Socket connection;
    private BufferedWriter output;
    private BufferedReader input;

    private String message = "";
    private boolean isFree;

    public Esp_Server() {
        isFree = true;
    }

    public void run() {
        try {

            server = new ServerSocket(serverPort, 100);
            while (true) {
                try {
                    waitForConnection();
                    setupStreams();
                    whileConnected();
                } catch (EOFException eofException) {
                    showMessage("Client terminated connection");
                } catch (IOException ioException) {
                    showMessage("Could not connect...");
                } finally {
                    closeStreams();
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private void waitForConnection() throws IOException {

        showMessage("Waiting for someone to connect...");
        connection = server.accept(); //once someone asks to connect, it accepts the connection to the socket this gets repeated fast
        showMessage("Now connected to " + connection.getInetAddress().getHostName()); //shows IP adress of client

    }

    private void setupStreams() throws IOException {

        showMessage("creating streams...");
        output = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        output.flush();
        input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        showMessage("Streams are setup!");

    }

    private void whileConnected() throws IOException {

        do {

            char x = (char) input.read();
            while (x != '\n') {
                message += x;
                x = (char) input.read();
            }
            showMessage(message);
            message = "";

        } while (!message.equals("END")); //if the user has not disconnected, by sending "END"

    }

    private void closeStreams() {

        showMessage("Closing streams...");
        try {
            output.close();
            input.close();
            connection.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void showMessage(final String message) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        if (message.equals("FULL")) {
                            isFree=  false;
                        }
                        else if (message.equals("FREE")) {
                            isFree = true;
                        }
                        //System.out.println(message);
                    }
                }
        );

    }

    public boolean getFree() {
        return isFree;
    }

}