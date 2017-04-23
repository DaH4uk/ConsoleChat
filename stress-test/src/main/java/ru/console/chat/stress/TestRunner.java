package ru.console.chat.stress;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by turov on 17.04.2017.
 */
class TestRunner {


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3000; i++) {
            final int n = i;
            new Thread(() -> {
                try {
                    test(n);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            Thread.sleep(200);
        }
    }

    private static void test(int n) throws InterruptedException {
        Socket socket;
        try {
            int PORT = 9000;
            String HOST = "localhost";
            socket = new Socket(HOST, PORT);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            writeMsg("testName" + n, printWriter);
            writeMsg("hello", printWriter);
            writeMsg("hello", printWriter);
            writeMsg("/help", printWriter);
            writeMsg("/users", printWriter);
            writeMsg("/online", printWriter);
            writeMsg("/name test" + n, printWriter);
            for (int i = 0; i < 100; i++) {
                writeMsg("randomMsg#" + i, printWriter);
            }
            writeMsg("bye of test" + n, printWriter);
            Thread.sleep(50000);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeMsg(String msg, PrintWriter printWriter) {
        printWriter.println(msg);
        printWriter.flush();

        try {
            Thread.sleep(Math.round(Math.random() * 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
