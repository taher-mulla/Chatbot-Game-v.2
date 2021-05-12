
import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {

        //creating a server socket
        try (ServerSocket serverSocket = new ServerSocket(5000)) {

            while (true) {

                //calling a client thread everytime a client tries to connect
                new ToClient(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            System.out.println("server error : " + e.getMessage());
        }


    }
}
