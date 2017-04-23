package ru.console.chat.services.commands;


import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by turov on 10.04.2017.
 */
public class ChangeNameCmdTest {
    private static ChangeNameCmd changeNameCmd;

    @Before
    public void setUp(){
        changeNameCmd = new ChangeNameCmd();
        changeNameCmd.userRepo.addUser("testAddress", "testUserName");
    }

    @Test
    public void invoke() {
        if (!changeNameCmd.userRepo.getUserAddresses().contains("testAddress")){
            changeNameCmd.userRepo.addUser("testAddress", "testUserName");
        }
        changeNameCmd.invoke("test", "testAddress");
        assertEquals(changeNameCmd.userRepo.getUserNameByAddress("testAddress"), "test");
    }

    @Test
    public void invoke_size() {
        int cnt = changeNameCmd.userRepo.getUserCount();

        changeNameCmd.invoke("test", "testAddress");
        int cnt2 = changeNameCmd.userRepo.getUserCount();
        assertEquals(cnt2, cnt);
    }

    @Test
    public void getDescription() {
        assertEquals(changeNameCmd.getDescription(), "Изменить имя");
    }

}