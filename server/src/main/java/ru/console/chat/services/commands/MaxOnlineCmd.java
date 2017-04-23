package ru.console.chat.services.commands;

import ru.console.chat.repo.UserRepo;
import ru.console.chat.repo.UserRepoImpl;

/**
 * Возвращает максимальный онлайн
 * @author Turov Danil
 * @date 19.04.2017.
 */
public class MaxOnlineCmd implements Command{
    private final UserRepo userRepo = UserRepoImpl.getInstance();

    /**
     * @param arg - нас не интересует
     * @param address - тоже не интересует
     * @return  Возвращает максимальный онлайн
     */
    @Override
    public String invoke(String arg, String address) {
        return "Максимальный онлайн был: " + userRepo.getMaxUserCount();
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "Осображает максимальное число пользователей в сессии";
    }
}
