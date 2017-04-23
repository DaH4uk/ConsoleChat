package ru.console.chat.services.commands;

import org.junit.Before;
import org.junit.Test;
import ru.console.chat.repo.UserRepo;
import ru.console.chat.repo.UserRepoImpl;

import static junit.framework.Assert.assertEquals;


/**
 * Created by turov on 11.04.2017.
 */
public class UsersCountCmdTest {
    private final UsersCountCmd usersCountCmd = new UsersCountCmd();

    @Before
    public void setUp() throws Exception {
        UserRepo userRepo = UserRepoImpl.getInstance();
        for (String address: userRepo.getUserAddresses()){
            userRepo.removeUserByAddress(address);
        }
        usersCountCmd.userRepo.addUser("testAddress1", "testUser1");
        usersCountCmd.userRepo.addUser("testAddress2", "testUser2");
    }

    @Test
    public void invoke() throws Exception {
        assertEquals(usersCountCmd.invoke(null, null), "Число подключенных пользователей: 2");
    }

}