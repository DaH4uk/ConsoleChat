package ru.console.chat.repo;

import java.util.Set;

/**
 * Обеспечивает связь с UserRepoImpl,
 * Имплементация может быть заменена в любой момент
 * @author Turov Danil
 * @date 03.04.2017.
 */
public interface UserRepo {

    Set<String> getUserAddresses();

    void addUser(String address, String userName);

    String getUserNameByAddress(String address);

    void removeUserByAddress(String address);

    boolean containsUserName(String userName);

    int getUserCount();

    String getMaxUserCount();

}
