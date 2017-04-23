package ru.console.chat;

import ru.console.chat.client.Client;
import ru.console.chat.client.ClientImpl;
import ru.console.chat.services.SocketServiceImpl;
import ru.console.chat.services.TransportService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс, отвечающий за запуск клиента
 * @author Turov Danil
 * @date 20.12.2016.
 */
class ClientRunner {

    /**
     * Запуск и инициализация
     */
    public static void main(String[] args) throws IOException {
        new ClientImpl().runClient();
    }
}
