package ru.console.chat.repo;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by turov on 10.04.2017.
 */
public class MessageRepoTest {
    private static final MessageRepo messageRepo = MessageRepoImpl.getInstance();
    @Before
    public void setUp() {
        for (int i = 0; i < messageRepo.getMessages().size(); i++) {
            messageRepo.getMessages().remove(i);
        }
    }

    @Test
    public void getMessages() {
        List<String> strings = new ArrayList<>();
        strings.add("test");
        messageRepo.addMessage("test");
        assertEquals(messageRepo.getMessages().contains("test"), true);
    }

    @Test
    public void addMessage() {
        int size = messageRepo.getMessages().size();
        messageRepo.addMessage("test1");
        assertEquals(messageRepo.getMessages().size(), size+1);
    }

    @Test
    public void add_More_100_messages(){
        for (int i = 0; i < 200; i++) {
            messageRepo.addMessage(i +"");
        }
        assertEquals(messageRepo.getMessages().size(), 100);
    }

}