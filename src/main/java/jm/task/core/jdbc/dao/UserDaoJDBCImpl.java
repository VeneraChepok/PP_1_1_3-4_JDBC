package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util util = new Util();
    private final String CREATE_TABLE = """ 
            create  table if not exists my_kata_db.Users(
            id bigint primary key not null auto_increment,
            name varchar(128),
            lastName varchar(128),
            age tinyint
            );
            """;
    private final String DROP_TABLE = """
            drop table if exists my_kata_db.users;
            """;

    private final String SAVE_USER = """
            insert into my_kata_db.users (name, lastName, age) values (?,?,?);
            """;

    private final String REMOVE_USERBYID = """
            delete from my_kata_db.users 
            where id=?;
            """;

    private final String ALL_USERS = """
            select * from my_kata_db.users;
            """;

    private final String CLEAN_USERS= """
            delete from my_kata_db.users;
            """;


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("table creation problems");
            e.printStackTrace();
        }
    }


    public void dropUsersTable() {
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("table deletion problems");
            e.printStackTrace();
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User with name - " + name + " added to database");
        } catch (SQLException e) {
            System.out.println("having problems saving or modifying");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USERBYID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User with id - " + id + "removed from database");
        } catch (SQLException e) {
            System.out.println("having removal problems");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                list.add(user);
                System.out.println(user);

            }
        } catch (SQLException ex) {
            System.out.println("having problems getting all users");
            throw new RuntimeException(ex);
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_USERS)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problems with user cleanup");
            e.printStackTrace();
        }
    }
}
