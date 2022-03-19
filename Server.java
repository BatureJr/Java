import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {

        final String FILE_NAME = "dictionary.txt";
        ServerSocket server = new ServerSocket(4494);
        Socket s = null;
        Scanner in = null;
        PrintWriter out = null;
        File inFile = null;
        Scanner readFile = null;

        while (true) {
            try {
                s = server.accept();
            } catch (IOException ex) {
                System.err.println("Accept failed");
                System.err.println("Exception: " + ex.getMessage());
                System.exit(1);
            }

            System.out.println("Accepted connection from client");

            try {
                in = new Scanner(s.getInputStream()); // Input stream from the client
                out = new PrintWriter(s.getOutputStream()); // Output stream to the client
                inFile = new File(FILE_NAME); // The dictionary file
                readFile = new Scanner(inFile); // Scanner which scans the file

                String input = null; // String holding the line taken from the file
                while (in.hasNextLine()) {
                    String date = new Date().toString();
                    String temp = in.next(); // String holding the word sent from the client
                    System.out.println("From the client " + temp);
                    while (readFile.hasNextLine()) { // While there are unread lines in the file
                        System.out.println("nextline");
                        input = readFile.nextLine(); // Store the unread line
                        System.out.println("From the file " + input);
                        if (input.contains(temp)) { // If the read line contains the word sent from the client
                            System.out.println("Check " + input + " " + temp);
                            out.println(date + " " + input); // Respond with the whole line containing the meaning in the other language
                            out.flush();
                        }
                        else {
                            out.println("No knowledge for " + temp);
                            out.flush();
                        }
                    }
                    System.out.println("Received: " + temp);
                }
            }
            catch (IOException ex) {
                System.err.println("Exception: " + ex.getMessage());
                System.out.println("Closing connection with client");

                out.close();
                in.close();
                System.exit(1);
            }
            out.close();
            in.close();
        }
    }
}
