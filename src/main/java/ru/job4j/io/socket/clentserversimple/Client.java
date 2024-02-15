package ru.job4j.io.socket.clentserversimple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("127.0.0.1", 8000);

        /**
         * can be BufferedWriter, too (let us write osw.newLine() instead of \n)
         */
        OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));

        /**
         * clientRequest
         */
        osw.write("get me some info! \r\n\r\n");
        osw.flush();

        String s;
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }

        osw.close();
        reader.close();
        clientSocket.close();
    }
}
