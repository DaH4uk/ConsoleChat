package ru.console.chat;

import ru.console.chat.server.ServerImpl;

import java.io.IOException;

/**
 * Запуск сервера
 * @author Turov Danil
 * @date 10.04.2017.
 */
class ServerRunner {
    public static void main(String[] args) throws IOException {
        ServerImpl.getInstance().start();
    }
}
