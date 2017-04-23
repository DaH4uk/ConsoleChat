package ru.console.chat.services.commands;

/**
 * Обеспечивает связь между командамыи CommandService
 * @author Turov Danil
 * @date 19.04.2017.
 */
public interface Command {
    /**
     * Вызов команды
     * @param arg - аргумент для команды
     * @param address - адрес клиента, вызвавшего команду
     * @return результат команды
     */
    String invoke(String arg, String address);

    /**
     * @return описание команды
     */
    String getDescription();
}
