package ru.console.chat.services;


import ru.console.chat.services.commands.Command;

import java.util.Map;

/**
 * Обеспечивает связь с CommandServiceImpl
 * @author Turov Danil
 * @date 03.04.2017.
 */
public interface CommandService {
    /**
     * Выполнить команду
     * @param cmd - название команды
     * @param address - адрес клиента, вызвавшего ее
     * @return результат выполнения команды
     */
    String invokeCommand(String cmd, String address);

    /**
     * @return карта команд
     */
    Map<String, Command> getCmdMap();
}
