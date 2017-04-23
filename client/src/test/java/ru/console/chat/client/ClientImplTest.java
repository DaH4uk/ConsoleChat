package ru.console.chat.client;

import org.junit.Test;
import ru.console.chat.services.SocketServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by turov on 23.04.2017.
 */
public class ClientImplTest {

    @Test
    public void runClientTest() throws Exception{
        ClientImpl client = new ClientImpl(){
            protected void initConsoleWriter(){
            }

            protected void initConsoleReader(){
            }
        };
        client.transportService = mock(SocketServiceImpl.class);
        client.consoleReader = mock(BufferedReader.class);
        when(client.consoleReader.readLine()).thenReturn("");
        client.runClient();
    }

    @Test
    public void runClientTestExeption() throws Exception{
        ClientImpl client = new ClientImpl(){
            protected void initConsoleWriter(){
            }

            protected void initConsoleReader(){
            }
        };
        client.transportService = mock(SocketServiceImpl.class);
        client.consoleReader = mock(BufferedReader.class);
        when(client.consoleReader.readLine()).thenThrow(IOException.class);
        client.runClient();
    }


}