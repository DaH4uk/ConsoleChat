package ru.console.chat.services.commands;

import ru.console.chat.services.CommandService;
import ru.console.chat.services.impl.CommandServiceImpl;

import java.util.Map;

/**
 * Возвращает список команд и их описание
 * @author Turov Danil
 * @date 28.03.2017.
 */
public class HelpCmd implements Command {
    final CommandService commandService = CommandServiceImpl.getInstance();

    /**
     * @param arg - нас не интересует
     * @param address - тоже не интересует
     * @return  Возвращает список команд и их описание
     */
    public String invoke(String arg, String address) {
        Map<String, Command> stringCommandMap = commandService.getCmdMap();
        StringBuilder builder = new StringBuilder();
        builder.append("Список комманд:\n");
        for (String s : stringCommandMap.keySet()) {
            builder.append("/")
                    .append(s)
                    .append(" - ")
                    .append(stringCommandMap.get(s).getDescription())
                    .append("\n");
        }
        builder.append("/exit - Выйти из чата");

        return builder.toString();
    }

    /**
     * @return описание команды
     */
    public String getDescription() {
        return "Список всех доступных команд";
    }
}
