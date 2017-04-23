package ru.console.chat.services.impl;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.console.chat.services.impl.CommandServiceImpl.cmdMap;

/**
 * Created by turov on 11.04.2017.
 */
public class CommandServiceImplTest {
    private final CommandServiceImpl commandService = CommandServiceImpl.getInstance();

    @Test
    public void find_Command_not_found() throws Exception {
        assertEquals(commandService.invokeCommand("testFail", null),
                "Команда не найдена. Введите /help для просмотра списка команд.\n");
    }
    @Test
    public void find_Command() throws Exception {
        assertEquals(commandService.invokeCommand("help", null),
                "Список комманд:\n" +
                        "/help - Список всех доступных команд\n" +
                        "/max_online - Осображает максимальное число пользователей в сессии\n" +
                        "/name - Изменить имя\n" +
                        "/online - Количество подключенных пользователей\n" +
                        "/users - Список пользователей\n" +
                        "/exit - Выйти из чата\n");
    }

    @Test
    public void getCmdMap() throws Exception {
        assertEquals(commandService.getCmdMap(), cmdMap);
    }

}