package ru.console.chat.services.commands;

import ru.console.chat.repo.UserRepo;
import ru.console.chat.repo.UserRepoImpl;

/**
 * Команда изменить имя пользователя.
 * @author Turov Danil
 * @date 03.04.2017.
 */
public class ChangeNameCmd implements Command {
    /**
     * Потребуется доступ к userRepo
     */
    final UserRepo userRepo = UserRepoImpl.getInstance();

    /**
     * Проверяем, чтобы таких имен еще не было зарегистрировано
     * и чтобы имя было не пустое.
     * и меняем имя
     * @param userName - имя пользователя
     * @param address - адрес пользователя
     * @return результат операции
     */
    @Override
    public String invoke(String userName, String address) {
        if ("".equals(userName)){
            return "Вы ввели пустое имя, попробуйте еще раз";
        }
        if (userRepo.containsUserName(userName)){
            return "Имя уже существует, попробуйте ввести другое";
        }
        userRepo.removeUserByAddress(address);
        userRepo.addUser(address, userName);
        return "Имя пользователя изменено на "+ userName;
    }

    /**
     * @return Описание команды
     */
    @Override
    public String getDescription() {
        return "Изменить имя";
    }
}
