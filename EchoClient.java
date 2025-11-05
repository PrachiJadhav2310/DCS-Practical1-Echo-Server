import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws Exception {
        String host = (args.length > 0) ? args[0] : "127.0.0.1";
        int port = (args.length > 1) ? Integer.parseInt(args[1]) : 5000;
        try (Socket socket = new Socket(host, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            System.out.println(in.readLine());
            String line;
            while (true) {
                System.out.print("you> ");
                line = stdin.readLine();
                if (line == null)
                    break;
                out.println(line);
                String resp = in.readLine();
                System.out.println(resp);
                if ("bye".equalsIgnoreCase(line))
                    break;
            }
        }
    }
}