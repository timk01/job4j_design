package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * создали сервер, пока не закрыт:
 * server.accept() - слушаем
 * output.write("HTTP/1.1 200 OK\r\n\r\n".getBytes()); - пишем ответочку, что все ОК (Клиенту)
 * = socket.getOutputStream();
 * output - это то, что ШЛЕМ ОБРАТНО уже МЫ
 * = new BufferedReader(new InputStreamReader(socket.getInputStream());
 * in - то, что мы читаем (что шлют НАМ)
 * <p> </p>
 * остальная логика не так важна, ервер работает до тех пор пока не закроем сами (красная кнопка) /
 * или введем кодовое слово
 */

public class EchoServer {
    private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class.getName());

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream output = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    output.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    String str = in.readLine();
                    System.out.println(str);
                    String message = str.split(" ")[1];
                    if ("/?msg=Hello".equals(message)) {
                        output.write("Hello".getBytes());
                    } else if ("/?msg=Exit".equals(message)) {
                        server.close();
                    } else {
                        output.write(message.substring("/?msg=".length()).getBytes());
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("IOException with sockets in EchoServer", e);
        }
    }
}