package ru.console.chat.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Реализация транспортного сервиса для клиента
 *
 * @author Turov Danil
 * @date 03.04.2017.
 */
public class SocketServiceImpl implements TransportService {
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    /**
     * Подключение к серверу
     * @param host адрес сервера
     * @param port порт сервера
     */
    public void connect(String host, int port){
        try {
            Socket socket = new Socket(host, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Запись в соккет
     * @param text текст
     */
    @Override
    public void write(String text) {
        printWriter.println(text);
        printWriter.flush();
    }

    /**
     * Чтение из соккета
     * @return полученная строка
     */
    @Override
    public String read() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            System.err.println("Вы отключены от сервера");
            System.exit(0);
        }
        return null;
    }
}
