package ru.console.chat.repo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Реализация интерфейса MessageRepo,
 * Отвечает за хранение сообщений в опративной памяти
 * @author Turov Danil
 * @date 03.04.2017.
 */
public class MessageRepoImpl implements MessageRepo {
    /**
     * Список сообщений
     */
    private final List<String> messages;

    /**
     * Поле, хранящее объект класса
     */
    private static MessageRepoImpl instance;

    /**
     * Решил сделать хранилище синглтоном,
     * Чтобы не пересоздавать постоянно объект класса
     * @return объект класса
     */
    public static synchronized MessageRepoImpl getInstance() {
        if (instance == null) {
            instance = new MessageRepoImpl();
        }
        return instance;
    }

    /**
     * Приватный конструктор,
     * чтобы никто не мог создать объект класса,
     * кроме как getInstance
     */
    private MessageRepoImpl(){
        messages = Collections.synchronizedList(new LinkedList<>());
    }

    /**
     * @return возвращает список сообщений
     */
    public List<String> getMessages(){
        return messages;
    }

    /**
     * Добавляем сообщения в хранилище,
     * Если их число больше 100, то удаляем первое сообщение
     * @param message - сообщение
     */
    public void addMessage(String message){
        if (messages.size() == 100){
            messages.remove(0);
        }
        messages.add(message);
    }






}
