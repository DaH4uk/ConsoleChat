package ru.console.chat.services;


/**
 * Обеспечиват связь с реализацией транспортного сервиса
 * @author Turov
 * @date 03.04.2017.
 */
public interface TransportService {
    /**
     * @return boolean - открыт ли канал передачи данных
     */
    boolean isOpen();

    /**
     * Ожидаем подключения и работаем
     */
    void handleConnection();

    /**
     * Чтение из канала
     * @param userAddress - адрес клиента
     */
    void read(String userAddress);

    /**
     * Удаление адреса
     * @param userAddress - адрес пользователя
     */
    void deleteAddress(String userAddress);

    /**
     * Отправляет сообщение одному пользователю
     * @param message - сообщение
     * @param userAddress - адрес пользователя
     */
    void sendMessageSingleUser(String message, String userAddress);

    /**
     * Разсылает сообщение всем, кроме текущего пользователя
     * @param message - сообщение
     * @param userAddress - адрес пользователя
     */
    void broadcastMessageExcept(String message, String userAddress);
}
