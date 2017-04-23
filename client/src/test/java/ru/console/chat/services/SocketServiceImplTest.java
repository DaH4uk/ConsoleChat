package ru.console.chat.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.*;

/**
 * Created by turov on 23.04.2017.
 */
public class SocketServiceImplTest {


    @Test
    public void write() throws Exception {
        SocketServiceImpl socketService = new SocketServiceImpl();
        socketService.printWriter = Mockito.mock(PrintWriter.class);
        socketService.write("sd");
    }

    @Test
    public void read() throws Exception {
        SocketServiceImpl socketService = new SocketServiceImpl();
        socketService.bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(socketService.bufferedReader.readLine()).thenReturn("test");
        assertEquals(socketService.read(),"test");
    }

    @Test
    public void readException() throws Exception {
        SocketServiceImpl socketService = new SocketServiceImpl();
        socketService.bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(socketService.bufferedReader.readLine()).thenThrow(IOException.class);
    }



}