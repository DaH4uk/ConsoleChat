package ru.console.chat.services.commands;

import ru.console.chat.repo.UserRepo;
import ru.console.chat.repo.UserRepoImpl;

/**
 * Возвращает количество подключенных пользователей
 * @author Turov Danil
 * @date 19.04.2017.
 */
public class UsersCountCmd implements Command {
    final UserRepo userRepo = UserRepoImpl.getInstance();

    /**
     * @param arg - нас не интересует
     * @param address - тоже не интересует
     * @return  количество подключенных пользователей
     */
    @Override
    public String invoke(String arg, String address) {
        return "Число подключенных пользователей: " + userRepo.getUserCount();
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "Количество подключенных пользователей";
    }
}
