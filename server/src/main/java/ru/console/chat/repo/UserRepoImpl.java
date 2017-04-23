package ru.console.chat.repo;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Реализация интерфейса UserRepo,
 * Отвечает за хранение списка пользователей
 *  и их адресов в опративной памяти
 * @author Turov Danil
 * @date 03.04.2017.
 */
public class UserRepoImpl implements UserRepo {
    /**
     * Реализаци класса
     */
    private static UserRepoImpl instance;
    /**
     * Карта, хранящая список адресов пользователей и их имен
     * address/userName
     */
    private final Map<String, String> userMap;
    /**
     * По дефолту число пользователей 0
     */
    private Integer userMaxCount = 0;

    /**
     * Синглтон
     * @return объект класса
     */
    public static synchronized UserRepoImpl getInstance() {
        if (instance == null) {
            instance = new UserRepoImpl();
        }
        return instance;
    }

    /**
     * Приватный конструктор
     */
    private UserRepoImpl() {
        userMap = new ConcurrentHashMap<>();
    }

    /**
     * @return Возвращает список адресов пользователей
     */
    @Override
    public Set<String> getUserAddresses() {
        return userMap.keySet();
    }

    /**
     * Добавляет позьзователя в список
     * и инкрементит число пользоватлей
     * @param address - адрес пользователя
     * @param userName - имя пользователя
     */
    @Override
    public void addUser(String address, String userName) {
        userMap.put(address, userName);
        if (userMap.size() > userMaxCount){
            userMaxCount = userMap.size();
        }
    }

    /**
     * Возвращает имя пользователя по адресу
     * @param address - адрес пользователя
     * @return userName - имя пользователя
     */
    @Override
    public String getUserNameByAddress(String address) {
        return userMap.get(address);
    }

    /**
     * Удаляет пользователя
     * @param address - адрес пользователя
     */
    @Override
    public void removeUserByAddress(String address) {
        userMap.remove(address);
    }

    /**
     * Проверяет, содержится ли пользователь в системе
     * @param userName - имя пользователя
     * @return boolean
     */
    @Override
    public boolean containsUserName(String userName) {
        return userMap.containsValue(userName);
    }

    /**
     * @return Количество пользователей
     */
    @Override
    public int getUserCount() {
        return userMap.size();
    }

    /**
     * @return Максимальный онлайн в сессии
     */
    @Override
    public String getMaxUserCount() {
        return userMaxCount.toString();
    }


}
