package ru.console.chat.client;

import ru.console.chat.services.SocketServiceImpl;
import ru.console.chat.services.TransportService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Содержит логику клиента
 * @author Turov Danil
 * @date 23.04.2017.
 */
public class ClientImpl implements Client {
    TransportService transportService  = new SocketServiceImpl();
    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Запускаем клиент
     */
    public void runClient(){
        String text;
        String host = "localhost";
        int port = 9000;

        try {
            System.out.println("Пожалуйста введите адрес сервера:");
            text = consoleReader.readLine();
            if (!"".equals(text)) {
                host = text;
            } else {
                host = "localhost";
                System.out.println("Будет использован адрес localhost");
            }
            System.out.println("Пожалуйста введите порт сервера:");
            text = consoleReader.readLine();
            if (!"".equals(text)) {
                port = Integer.parseInt(text);
            } else {
                port = 9000;
                System.out.println("Будет использован адрес 9000");
            }

        } catch (IOException e) {
            System.out.println("Произошла ошибка инициализации.");
            e.printStackTrace();
            System.out.println("Будет использован адрес localhost");
            System.out.println("Будет использован адрес 9000");
        }

        transportService.connect(host, port);
        System.out.println("Вы упешно подключились к серверу!");
        initConsoleWriter();
        initConsoleReader();
    }

    void initConsoleWriter(){
        new Thread(() -> {
            String line;
                while ((line = transportService.read()) != null) {
                    System.out.println(line);
                    System.out.flush();
                }
        }).start();
    }

    void initConsoleReader(){
        String text;
        try {
            while ((text = consoleReader.readLine()) != null) {
                if ("/exit".equals(text)) {
                    System.err.println("Вы отключены от сервера");
                    System.exit(0);
                }
                transportService.write(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
