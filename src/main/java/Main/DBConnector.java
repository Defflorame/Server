package Main;

import java.sql.*;

public class DBConnector {
    public static void connect() {
        // Данные для подключения к базе данных
        String url = "jdbc:mysql://localhost:3306/ShopDB"; // URL базы данных
        String user = "Server"; // Имя пользователя (замените на своё)
        String password = "11111111"; // Пароль (замените на свой)

        // Попробуем подключиться к базе данных
        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            System.out.println("Connection to MySQL database successful!");

            // Создаём запрос к таблице "users"
            String query = "SELECT * FROM users";

            // Выполняем запрос и выводим результаты
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("user_id");
                    String name = resultSet.getString("username");
                    String email = resultSet.getString("password");
                    int role_id = resultSet.getInt("role_id");
                    System.out.println("User_id " + id);
                    System.out.println("Username " + name);
                    System.out.println("Password " + password);
                    System.out.println("Role_id " + role_id);

                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error connecting to the database.");
            e.printStackTrace();

        }
        finally {
           // connection.close();
        }
    }
}
