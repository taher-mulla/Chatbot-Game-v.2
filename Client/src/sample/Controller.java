package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Controller {

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField messageArea;
    @FXML
    private TextArea mainTextArea;


    private int botP = 0, userP = 0;
    private String botS = "Bot's points --> ", userS = "Users points are -->";
    private TextField botPoints = new TextField(botS + botP);
    private TextField userPoints = new TextField(userS + userP);
    private VBox vBox = new VBox(botPoints, userPoints);


    Socket socket;
    BufferedReader fromServer;
    PrintWriter toServer;

    String turn = "";


    public void initialize() {


        borderPane.setTop(vBox);
        addToTextArea("server", "You are now playing a game with a server,\n" +
                "                  Each of you will give the name of a movie character and must reply with the movie name\n" +
                "                  The server will ask first!!");


        //creating a connection with the server
        try {
            //socket, IP, and OP to connect to a server
            socket = new Socket("localHost", 5000);
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServer = new PrintWriter(socket.getOutputStream(), true);

            //receiving text from the server and displaying it on the UI
            String textFromServer = fromServer.readLine();
            String[] split = textFromServer.split(";");
            addToTextArea(split[0], split[1]);

        } catch (IOException e) {
            System.out.println("Client error : " + e.getMessage());
            e.getStackTrace();
        }

    }


    public void sendButton() {

        //sending a Movie OR a Character name to the server
        String message = messageArea.getText();
        addToTextArea("y", message);
        toServer.println(message);


        try {

            while (true) {

                //server will reply with a string(wrong or right and more data)
                String string = fromServer.readLine();
                System.out.println(string);

                //display on the GUI and updating the score
                String[] split = string.split(";");
                //parsing the string points value to integer
                botP = Integer.parseInt(split[2]);
                userP = Integer.parseInt(split[3]);
                //updating the score
                botPoints.setText(botS + botP);
                userPoints.setText(userS + userP);
                //updating the GUI text
                addToTextArea(split[0], split[1]);

                //server will ask for a character name or movie name
                string = fromServer.readLine();
                System.out.println(string);
                split = string.split(";");

                //parsing the string points value to integer
                botP = Integer.parseInt(split[2]);
                userP = Integer.parseInt(split[3]);
                //updating the score
                botPoints.setText(botS + botP);
                userPoints.setText(userS + userP);
                //updating the GUI text
                addToTextArea(split[0], split[1]);

                break;

            }
        } catch (IOException e) {
            System.out.println("Error in sendButton : " + e.getMessage());
            e.printStackTrace();
        }

    }

    public synchronized void addToTextArea(String who, String addText) {
        System.out.println("this is from = who = " + who + "  " + who.equals("server"));
        System.out.println("text to add is : " + addText);
        if (who.equals("server")) {
            mainTextArea.appendText("\n" + "[Server]-> " + addText);
        } else {
            mainTextArea.appendText("\n" + "[Client]-> " + addText);
        }
    }

}
