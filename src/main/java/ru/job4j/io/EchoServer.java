package ru.job4j.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream output = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    StringBuilder sb = new StringBuilder();
                    for (String str = in.readLine(); str != null && !str.isEmpty(); str = in.readLine()) {
                        System.out.println(str);
                        sb.append(str + System.lineSeparator());
                    }
                    output.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    output.flush();
                    if (sb.toString().contains("/?msg=Bye")) {
                        output.write("See you".getBytes());
                        output.flush();
                        server.close();
                    }
                }
            }
        }
    }
}