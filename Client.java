import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        {
            Socket s = null;
            Scanner in = null;
            PrintWriter out = null;

            try {
                s = new Socket("192.168.43.225", 4494);
                in = new Scanner(s.getInputStream()); // Input stream from the server
                out = new PrintWriter(s.getOutputStream()); // Output stream to the server
            } catch (UnknownHostException ex) {
                System.err.println("Unknown host: " + 4494);
                System.err.println("Exception: " + ex.getMessage());
                System.exit(1);

            } catch (IOException ex) {
                System.err.println("Cannot get I/O for " + 4494);
                System.err.println("Exception: " + ex.getMessage());
                System.exit(1);
            }

            Scanner user = new Scanner(System.in); // Scanning for user input
            String input;

            while (user.hasNext()) {
                input = user.next(); // Hold the input from the user

                out.println(input); // Send it to the server
                out.flush();

                System.out.println("Response: " + in.nextLine());
            }

            out.close();
            in.close();
            s.close();
        }
    }
}