package ru.job4j.io.socket.clentserversimple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(8000);

        int client = 0;
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client accepted " + client++);

            OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String clientRequest = reader.readLine();
            Thread.sleep(3000);
            String serverResponse = "#" + client + ", your incoming msg length " + clientRequest.length();

            osw.write("HTTP/1.1 200 OK\r\n\r\n");
            osw.write("client: " + serverResponse + "\r\n\r\n");

            osw.flush();

            reader.close();
            osw.close();
            clientSocket.close();
        }
    }
}
