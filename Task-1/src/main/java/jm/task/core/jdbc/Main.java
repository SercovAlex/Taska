package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.postgresql.Driver;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userDao = new UserServiceImpl();
        userDao.createUsersTable();
        userDao.dropUsersTable();
        userDao.saveUser("Ivan","Ivanov", (byte) 25);
        userDao.saveUser("Petr","Petrov", (byte) 342);
        userDao.saveUser("Alek","Aleks", (byte) 50);
        userDao.saveUser("Sergey","Sergeev", (byte) 21);
        userDao.removeUserById(4);
        List<User> userList1 = userDao.getAllUsers();
        userList1.forEach(System.out::println);
        userDao.cleanUsersTable();
    }
}
