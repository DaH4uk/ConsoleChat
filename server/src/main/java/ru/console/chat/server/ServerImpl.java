package ru.console.chat.server;

import ru.console.chat.repo.UserRepo;
import ru.console.chat.repo.UserRepoImpl;
import ru.console.chat.services.CommandService;
import ru.console.chat.repo.MessageRepo;
import ru.console.chat.services.impl.CommandServiceImpl;
import ru.console.chat.repo.MessageRepoImpl;
import ru.console.chat.services.impl.SocketServiceImpl;
import ru.console.chat.services.TransportService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Реализация интерфейса Server,
 * Отвечает за бизнес-логику приложения
 *
 * @author Turov Danil
 * @date 20.12.2016.
 */
public class ServerImpl implements Server {
    private static TransportService transportService;
    private static MessageRepo messageRepo;
    private static UserRepo userRepo;
    private static CommandService commandService;
    private static String address;
    private static int port;
    private static ServerImpl instance;
    private static BufferedReader consoleReader;

    /**
     * Приватный конструктор, где
     * инициализируются некоторые поля
     */
    private ServerImpl() {
        messageRepo = MessageRepoImpl.getInstance();
        userRepo = UserRepoImpl.getInstance();
        commandService = CommandServiceImpl.getInstance();
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Сигнлтон.
     * Добавил логику для установки
     * адреса и порта
     *
     * @return объект класса
     */
    public static synchronized ServerImpl getInstance() {
        if (instance == null) {
            instance = new ServerImpl();
            System.out.println("Please enter address for server:");
            try {
                String text = consoleReader.readLine();
                if (!"".equals(text)) {
                    address = text;
                } else {
                    address = "localhost";
                    System.out.println("Set the default address - localhost");
                }
                System.out.println("Please enter port for server:");
                text = consoleReader.readLine();
                if (!"".equals(text)) {
                    port = Integer.parseInt(text);
                } else {
                    port = 9000;
                    System.out.println("Set the default post - 9000");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return instance;
    }


    /**
     * Метод, который начинает работу сервера
     */
    public void start() {
        transportService = new SocketServiceImpl(address, port);    // создаем объект сервиса, отвечающего за передачу данных
        System.out.println("Server started on " + address + ":" + port);
        runServerConsoleReader();   //запускаем чтение из консоли на случай, если нужно будет остановить сервер
        /*
          Пока соединение открыто, работаем
         */
        while (transportService.isOpen()) {
            transportService.handleConnection();
        }
    }

    /**
     * Логика для события onMessage,
     * при поступлении сообщения на сервер
     *
     * @param userAddress - адрес, откуда пришло сообщение
     * @param string      - строка, которая пришла на сервер
     */
    public void onMessage(String userAddress, String string) {
        String message;

        if (userRepo.getUserNameByAddress(userAddress) == null) {   //если клиента нет в списке клиентов
            String userName = string.substring(0, string.length() - 2); //берем его имя
            if (userRepo.containsUserName(userName)) {  //проверяем, чтобы уже не было другого клиента с таким именем
                transportService.sendMessageSingleUser("Пользователь с таким именем уже зарегистрирован, пожалуйста введите другое имя.\n", userAddress);   //такой клиент уже есть
            } else if ("".equals(userName)) {
                transportService.sendMessageSingleUser("Имя не должно быть пустым.\n", userAddress);
            } else {
                userRepo.addUser(userAddress, userName);    //добавляем пользователя
                message = userName + ", присоединился к чату!\n";
                System.out.println(message + "Max online: " + userRepo.getMaxUserCount());  //чтобы отслеживать максимальный онлайн
                transportService.broadcastMessageExcept(message, userAddress);  //отсылаем сообщение всем, кроме этого клиента

                message = "Вы подключились к серверу!\n";
                if (messageRepo.getMessages().size() > 0) { //выводим клиенту список последних сообщений
                    message = message + "Последние сообщения:\n";
                }
                transportService.sendMessageSingleUser(message, userAddress);
                for (String msg : messageRepo.getMessages()) {
                    transportService.sendMessageSingleUser(msg, userAddress);
                }
                transportService.sendMessageSingleUser("Для получения списка комманд, введите /help\n", userAddress);
            }
        } else {
            if (string.indexOf("/") == 0) { //логика на случай, если клиент ввел пользовательскую команду
                String cmd = string.substring(1, string.length() - 2);
                transportService.sendMessageSingleUser(commandService.invokeCommand(cmd, userAddress), userAddress);
            } else {
                //если просто сообщение, то пересылаем его
                message = userRepo.getUserNameByAddress(userAddress) + ": " + string;
                transportService.broadcastMessageExcept(message, userAddress);
                messageRepo.addMessage(message);
            }
        }
    }

    /**
     * Логика для события onRead,
     * Читаем, если пришло сообщение или команда
     *
     * @param userAddress - адрес, откуда пришло событие
     */
    public void onRead(String userAddress) {
        transportService.read(userAddress);
    }

    /**
     * Логика, если подключен новый клиент
     *
     * @param address - адрес клиента
     */
    public void onConnect(String address) {
        String welcomeMsg = "Пожалуйста введите ваше имя: \n";
        transportService.sendMessageSingleUser(welcomeMsg, address);
    }

    /**
     * Логика, если клиент был отключен
     *
     * @param userAddress - адрес отключаемого клиента
     */
    public void onDisconnect(String userAddress) {
        transportService.deleteAddress(userAddress);
        String userName = userRepo.getUserNameByAddress(userAddress);
        userRepo.removeUserByAddress(userAddress);
        transportService.broadcastMessageExcept(userName + " покинул чат\n", userAddress);
        System.out.println(userName + " покинул чат");
    }

    /**
     * Запускаем чтение из консоли
     * команды /stop
     */
    private void runServerConsoleReader() {
        new Thread(() -> {
            System.out.println("Please enter /stop to stop the server");
            String text;
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while ((text = consoleReader.readLine()) != null) {
                    if ("/stop".equals(text)) {
                        System.err.println("The server has stopped");
                        System.exit(0);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
