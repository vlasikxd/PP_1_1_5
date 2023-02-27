package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        final UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Ivan", "Frolov", (byte)33);
        userService.saveUser("Max", "Sokolov", (byte)55);
        userService.saveUser("Denis", "Vlasov", (byte)44);
        userService.saveUser("Pablo", "Escobar", (byte)66);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
