package ru.console.chat.services.commands;

import org.junit.Before;
import org.junit.Test;
import ru.console.chat.repo.UserRepo;
import ru.console.chat.repo.UserRepoImpl;

import static org.junit.Assert.*;

/**
 * Created by turov on 23.04.2017.
 */
public class MaxOnlineCmdTest {
    private final MaxOnlineCmd maxOnlineCmd = new MaxOnlineCmd();
    private final UserRepo userRepo = UserRepoImpl.getInstance();

    @Test
    public void invoke() throws Exception {
        assertEquals(maxOnlineCmd.invoke(null, null), "Максимальный онлайн был: " + userRepo.getMaxUserCount());
    }

}