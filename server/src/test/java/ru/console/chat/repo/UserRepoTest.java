package ru.console.chat.repo;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;


/**
 * Created by turov on 10.04.2017.
 */
public class UserRepoTest {
    private static UserRepo userRepo;

    @Before
    public void setUp() {
        userRepo = UserRepoImpl.getInstance();
        userRepo.addUser("testAddress1", "testUserName1");
        userRepo.addUser("testAddress2", "testUserName2");
        userRepo.addUser("testAddress3", "testUserName3");
    }

    @After
    public void tearDown() {
        userRepo.removeUserByAddress("testAddress1");
        userRepo.removeUserByAddress("testAddress2");
        userRepo.removeUserByAddress("testAddress3");
        userRepo.removeUserByAddress("testAddress");
    }


    @Test
    public void getUserAddresses() {
        Set<String> testSet = userRepo.getUserAddresses();
        for (String adress : userRepo.getUserAddresses()) {
            userRepo.removeUserByAddress(adress);
        }
        for (int i = 0; i < 2; i++) {
            userRepo.addUser("test" + i, "test" + i);
        }

        int i = 0;
        for (String s : testSet) {
            assertEquals(testSet.contains("test" + i), true);
            i++;
        }
    }

    @Test
    public void addUser() {
        int count = userRepo.getUserCount();
        userRepo.addUser("testAddress", "testUserName");
        assertEquals(userRepo.getUserCount(), count+1);
    }

    @Test
    public void getUserNameByAddress() {
        assertEquals(userRepo.getUserNameByAddress("testAddress1"), "testUserName1");
    }

    @Test
    public void removeUserByAddress() {
        int count = userRepo.getUserCount();
        userRepo.removeUserByAddress("testAddress1");
        assertEquals(userRepo.getUserCount(), count-1);
    }

    @Test
    public void contains() {
        assertEquals(userRepo.containsUserName("testUserName1"), true);
        assertEquals(userRepo.containsUserName("testAddress"), false);
    }

    @Test
    public void getUserCount() {
        for (String adress : userRepo.getUserAddresses()) {
            userRepo.removeUserByAddress(adress);
        }
        userRepo.addUser("test", "test");
        assertEquals(userRepo.getUserCount(), 1);
    }

    @Test
    public void getMaxUserCount() {
        for (String adress : userRepo.getUserAddresses()) {
            userRepo.removeUserByAddress(adress);
        }
        for (int i = 0; i < 10; i++) {
            userRepo.addUser("test" + i, "test" + i);
        }
        assertEquals(userRepo.getMaxUserCount(), "10");
    }

}