package ru.console.chat.services.commands;

import org.junit.Before;
import org.junit.Test;
import ru.console.chat.repo.UserRepo;
import ru.console.chat.repo.UserRepoImpl;

import static junit.framework.Assert.assertEquals;

/**
 * Created by turov on 11.04.2017.
 */
public class UserListCmdTest {
    private final UserListCmd userListCmd = new UserListCmd();

    @Before
    public void setUp() throws Exception {
        UserRepo userRepo = UserRepoImpl.getInstance();
        for (String address: userRepo.getUserAddresses()){
            userRepo.removeUserByAddress(address);
        }
        userListCmd.userRepo.addUser("testAddress1", "testUser1");
        userListCmd.userRepo.addUser("testAddress2", "testUser2");
    }

    @Test
    public void invoke() throws Exception {
        String inv = userListCmd.invoke(null, null);
        assertEquals(inv, "Список пользователей:\n" +
                "testUser1\n" +
                "testUser2\n");
    }


}