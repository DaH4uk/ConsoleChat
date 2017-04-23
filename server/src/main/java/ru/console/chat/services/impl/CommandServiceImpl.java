package ru.console.chat.services.impl;

import ru.console.chat.services.CommandService;
import ru.console.chat.services.commands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Возвращает количество подключенных пользователей
 * @author Turov Danil
 * @date 02.04.2017.
 */
public class CommandServiceImpl implements CommandService {
    /**
     * Список всех команд
     * command name/command object
     */
    static final Map<String, Command> cmdMap = new HashMap<>();
    private static CommandServiceImpl instance;

    /**
     * Синглтон
     * заодно заполняем список команд
     * @return объект класса
     */
    public static synchronized CommandServiceImpl getInstance() {
        if (instance == null) {
            instance = new CommandServiceImpl();

            cmdMap.put("help", new HelpCmd());
            cmdMap.put("users", new UserListCmd());
            cmdMap.put("online", new UsersCountCmd());
            cmdMap.put("name", new ChangeNameCmd());
            cmdMap.put("max_online", new MaxOnlineCmd());
        }
        return instance;
    }

    /**
     * Ищет нужную команду и выполняет ее
     * @param cmd - назнание команды
     * @param address - адрес клиента, вызвавшего комманду
     * @return
     */
    public String invokeCommand(String cmd, String address) {
        String[] strings = cmd.split(" ");
        Command command = cmdMap.get(strings[0]);
        if (command == null) {
            return "Команда не найдена. Введите /help для просмотра списка команд.\n";
        } else {
            if (strings.length > 1) {
                return command.invoke(strings[1], address) + "\n";
            } else {
                return command.invoke("", address) + "\n";
            }
        }
    }

    /**
     * @return получить список команд
     */
    public Map<String, Command> getCmdMap() {
        return cmdMap;
    }
}
