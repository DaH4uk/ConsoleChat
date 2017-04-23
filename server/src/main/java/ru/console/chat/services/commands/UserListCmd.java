package ru.console.chat.services.commands;


import ru.console.chat.repo.UserRepo;
import ru.console.chat.repo.UserRepoImpl;

/**
 * Created by turov on 28.03.2017.
 */
public class UserListCmd implements Command {
    final UserRepo userRepo = UserRepoImpl.getInstance();
    @Override
    public String invoke(String arg, String address) {
        StringBuilder builder = new StringBuilder();
        builder.append("Список пользователей:\n");
        for (String userAddress : userRepo.getUserAddresses()){
            builder.append(userRepo.getUserNameByAddress(userAddress)).append("\n");
        }
        return builder.toString();
    }

    @Override
    public String getDescription() {
        return "Список пользователей";
    }
}
