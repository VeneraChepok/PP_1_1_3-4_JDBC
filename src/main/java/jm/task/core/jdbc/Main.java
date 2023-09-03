package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan","Ivanov", (byte) 20);
        userService.saveUser("Petr","Petrov", (byte) 22);
        userService.saveUser("Kolya","Smirnov", (byte) 18);
        userService.saveUser("Tom","Ford", (byte) 36);
        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
