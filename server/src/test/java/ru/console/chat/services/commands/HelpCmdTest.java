package ru.console.chat.services.commands;

import org.junit.*;

import static org.junit.Assert.*;


/**
 * Created by turov on 10.04.2017.
 */
public class HelpCmdTest {
    private static final HelpCmd helpCmd = new HelpCmd();


    @Test
    public void invoke() {
        String invoked = helpCmd.invoke("test", "test");
        for (String cmd : helpCmd.commandService.getCmdMap().keySet()){
            assertEquals(invoked.contains(cmd),true);
            Command command = helpCmd.commandService.getCmdMap().get(cmd);
            assertEquals(invoked.contains(command.getDescription()),true);
        }
        String string = "Список комманд:\n"+
                "/help - Список всех доступных команд\n"+
                "/max_online - Осображает максимальное число пользователей в сессии\n"+
                "/name - Изменить имя\n"+
                "/online - Количество подключенных пользователей\n"+
                "/users - Список пользователей\n"+
                "/exit - Выйти из чата";
        assertEquals(helpCmd.invoke(null, null), string);
    }

    @Test
    public void getDescription() {
        assertEquals(helpCmd.getDescription(), "Список всех доступных команд");
    }

}