import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Random;

public class ToClient extends Thread {

    String[] character = {"CHICO", "COOPER", "GRIFFIN", "EDWARDS", "EDWARDS", "JASON", "GWEN", "ELAINE",
            "VENKMAN", "DEPUTY CLARK", "GENERAL GREY", "TROY"};

    private int botP = 0, userP = 0;

    private Socket socket;
    String turn = "QToClient";

    public ToClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            //creating a input and output stream for the client
            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputToClient = new PrintWriter(socket.getOutputStream(), true);

            while (true) {

                //in these statements we check whose turn this is
                //Question to Client i.e. the server will give a Character name and will get a reply
                //OR
                //Question from Client i.e. the client will give a Character name
                if (turn == "QToClient") {

                    //giving the client a character, reply will be a movie name
                    String given = character[new Random().nextInt(character.length)];
                    outputToClient.println("server;Which movie is " + given + " from;" + botP + ";" + userP);

                    //reply from client, i.e. the movie name
                    String fromClient = inputFromClient.readLine();
                    outputToClient.println(checkMovieCorrect(fromClient, given));

                    //asking the client for a character
                    outputToClient.println("server;Pls send a character name;" + botP + ";" + userP);

                    //changing the turn
                    turn = "QFromClient";

                } else if (turn == "QFromClient") {

                    //getting a character name from the client, server will reply with a movie
                    String fromClient = inputFromClient.readLine();
                    System.out.println("received message from client : " + fromClient);

                    //sending a the movie name to the client, or sending "I don't know which movie this character is from"
                    outputToClient.println(characterFromWhichMovie(fromClient));

                    //changing the turn
                    turn = "QToClient";
                }

            }


        } catch (IOException e) {
            System.out.println("Error in client : " + e.getMessage());
        }
    }


    //THIS FUNCTION CHECKS IF THE GIVEN CHAR NAME IS THERE IN THE FILE AND RETURNS THE FIRST MATCH IT FINDS
    //THE FIRST MATCH(MOVIE) IS PUT IN ANOTHER BIGGER STRING WITH MULTIPLE PARTS SEPARATED BY ';'
    private String characterFromWhichMovie(String reply) {


        Path movieChar = FileSystems.getDefault().getPath("cornell movie-dialogs corpus", "movie_characters_metadata.txt");
        try (BufferedReader fileReader = Files.newBufferedReader(movieChar)) {
            System.out.println("IN characterExixts() TRY BLOCK");
            String line;
            String[] parts;

            //reading the txt file line by line and checking to find the movie
            while ((line = fileReader.readLine()) != null) {
                parts = line.split(";");
                System.out.println(parts[1].toLowerCase() + " ------------- " + reply.toLowerCase());
                if (parts[1].toLowerCase().equals(reply.toLowerCase())) {
                    //returned only the first movie it sees
                    botP++;
                    return "server;" + reply.toUpperCase() + " is from " + parts[3].toUpperCase() + ";" + botP + ";" + userP;

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        userP++;
        return "server;I don't know the answer;" + botP + ";" + userP;
    }


    //this function will check if the replied character is from the from given movie
    private String checkMovieCorrect(String reply, String given) {
        System.out.println("CHECKING IF MOVIE IS CORRECT");
        String line;
        String[] parts;

        Path movieChar = FileSystems.getDefault().getPath("cornell movie-dialogs corpus", "movie_characters_metadata.txt");
        try (BufferedReader fileReader = Files.newBufferedReader(movieChar)) {

            //checking line by line to find if the given answer is correct
            while ((line = fileReader.readLine()) != null) {
                parts = line.split(";");
                if (given.toLowerCase().equals(parts[1]) && parts[3].toLowerCase().equals(reply.toLowerCase())) {
                    userP++;
                    return "server;correct " + reply.toUpperCase() + " is from " + parts[3].toUpperCase() + ";" + botP + ";" + userP;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        botP++;
        return "server;" + given.toUpperCase() + " is not from " + reply.toUpperCase() + " you are are wrong;" + botP + ";" + userP;
    }

}
