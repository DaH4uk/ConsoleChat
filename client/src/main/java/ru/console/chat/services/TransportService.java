package ru.console.chat.services;

/**
 * Содержит описание методов,
 * которые должен реализовать транспортный сервис
 * @author Turov Danil
 * @date 03.04.2017.
 */
public interface TransportService {

    void write(String text);

    String read();

    void connect(String host, int port);

}
