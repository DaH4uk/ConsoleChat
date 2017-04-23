package ru.console.chat.services.impl;

import ru.console.chat.server.Server;
import ru.console.chat.server.ServerImpl;
import ru.console.chat.services.TransportService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Реализация транспортного сервиса.
 * @author Turov Danil
 * @date 03.04.2017.
 */
public class SocketServiceImpl implements TransportService {
    private ServerSocketChannel serverSocketChannel;
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(256);
    private Selector selector;
    private final Server server = ServerImpl.getInstance();
    private final Map<String, SocketChannel> userChannelMap = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newWorkStealingPool();

    /**
     * @return boolean - открыт или нет канал передачи данных
     */
    public boolean isOpen() {
        return serverSocketChannel.isOpen();
    }

    /**
     * Конструктор, в котором заодно заполняются некоторые поля,
     * @param address - адрес сервера
     * @param port - порт сервера
     */
    public SocketServiceImpl(String address, int port) {
        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            this.serverSocketChannel.socket().bind(new InetSocketAddress(address, port));
            this.serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            this.selector = selector;

            this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Выбираем селектор,
     * через итератор проходимся по всем ключам,
     * если ключ входящего соединения, то регистрируем его
     * и вызывает onConnect.
     * Если ключ читаемый, то onRead
     */
    public void handleConnection() {
        try {
            selector.select();

            Iterator iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) iterator.next();
                iterator.remove();
                if (selectionKey.isValid()) {

                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        String address = getAddress(socketChannel);
                        socketChannel.configureBlocking(false);
                        socketChannel.register(this.selector, SelectionKey.OP_READ, address);
                        userChannelMap.put(address, socketChannel);
                        server.onConnect(address);
                    }
                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        String address = getAddress(socketChannel);
                        server.onRead(address);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получить адрес для канала
     * @param socketChannel - сокет канал
     * @return адрес клиента
     */
    private String getAddress(SocketChannel socketChannel) {
        String address = "";
        if ("".equals(address)) {
            address = (new StringBuilder(socketChannel.socket().getInetAddress().toString()))
                    .append(":")
                    .append(socketChannel.socket().getPort()).toString();
        }
        return address;
    }

    /**
     * Получить канал по адресу
     * @param userAddress - адрес
     * @return сокет канал
     */
    private synchronized SocketChannel getChannelByAddress(String userAddress) {
        SocketChannel socketChannel = userChannelMap.get(userAddress);
        if (socketChannel == null) {
            userChannelMap.remove(userAddress);
        }
        return socketChannel;
    }

    /**
     * Чтение из сокет-канала
     * @param userAddress адрес клиента
     */
    public void read(String userAddress) {
        SocketChannel socketChannel = getChannelByAddress(userAddress);
        StringBuilder stringBuilder = new StringBuilder();
        byteBuffer.clear();
        int read;
        if (socketChannel != null)
            try {
                while ((read = socketChannel.read(byteBuffer)) > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.limit()];
                    byteBuffer.get(bytes);
                    stringBuilder.append(new String(bytes));
                    byteBuffer.clear();
                }
                if (read < 0) {
                    server.onDisconnect(userAddress);
                    socketChannel.close();
                } else {
                    server.onMessage(userAddress, stringBuilder.toString());
                }
            } catch (IOException e) {
                server.onDisconnect(userAddress);
                try {
                    socketChannel.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

    }

    /**
     * Запись в соккет-канал,
     * добавил многопоточность, чтобы работало немного быстрее
     * @param message - сообщение
     * @param address - адресс
     */
    private void write(String message, String address) {
        executorService.submit(() -> {
            ByteBuffer msgBuf = ByteBuffer.wrap(message.getBytes());
            SocketChannel socketChannel = getChannelByAddress(address);
            try {
                if (socketChannel != null)
                    socketChannel.write(msgBuf);
            } catch (IOException e) {
                e.printStackTrace();
                server.onDisconnect(address);
            }
            msgBuf.rewind();
        });

    }

    /**
     * Удаляем адрес пользователя из userChannelMap
     * @param userAddress - адресс пользователя
     */
    public void deleteAddress(String userAddress) {
        SocketChannel socketChannel = getChannelByAddress(userAddress);
        if (socketChannel != null)
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        userChannelMap.remove(userAddress);
    }

    /**
     * Отправить сообщение одному пользователю
     * @param message - сообщение
     * @param userAddress - адрес
     */
    @Override
    public void sendMessageSingleUser(String message, String userAddress) {
        write(message, userAddress);
    }

    /**
     * Разослать сообщение всем, кроме данного пользователя
     * @param message - сообщение
     * @param userAddress - адрес пользователя
     */
    @Override
    public void broadcastMessageExcept(String message, String userAddress) {
        for (String address : userChannelMap.keySet()) {
            if (!userAddress.equals(address)) {
                write(message, address);
            }
        }
    }
}


