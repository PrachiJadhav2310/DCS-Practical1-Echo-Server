import java.io.*;
import java.net.*;

public class EchoServer {
    private static class ClientHandler extends Thread {
        private final Socket socket;

        ClientHandler(Socket s) {
            this.socket = s;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                String line;
                out.println("Welcome to EchoServer. Type 'bye' to quit.");
                while ((line = in.readLine()) != null) {
                    if (line.equalsIgnoreCase("bye")) {
                        out.println("bye");
                        break;
                    }
                    out.println("echo: " + line);
                }
            } catch (IOException e) {
                System.err.println("Client error: " + e.getMessage());
            } finally {
                System.out.println("Client disconnected: " + socket.getRemoteSocketAddress());
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 5000;
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("EchoServer listening on port " + port);
            while (true) {
                Socket client = server.accept();
                System.out.println("Connected: " + client.getRemoteSocketAddress());
                new ClientHandler(client).start();
            }
        }
    }
}