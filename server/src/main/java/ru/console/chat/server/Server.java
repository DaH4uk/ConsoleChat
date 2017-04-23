package ru.console.chat.server;

/**
 * Обеспечивает связь с ServerImpl,
 * Имплементация может быть заменена в любой момент
 * @author Turov Danil
 * @date 10.04.2017.
 */
public interface Server {
    void onConnect(String address);

    void onRead(String address);

    void onDisconnect(String userAddress);

    void onMessage(String userAddress, String s);

}
