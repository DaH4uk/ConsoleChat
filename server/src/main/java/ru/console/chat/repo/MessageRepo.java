package ru.console.chat.repo;

import java.util.List;

/**
 * Обеспечивает связь с MessageRepoImpl,
 * Имплементация может быть заменена в любой момент
 * @author Turov Danil
 * @date 03.04.2017.
 */
public interface MessageRepo {

    List<String> getMessages();

    void addMessage(String msg);

}
