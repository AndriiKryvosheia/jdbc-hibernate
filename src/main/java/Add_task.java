import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Calendar;

public class Add_task {

    private static final String URL = "jdbc:mysql://localhost:3306/MyJoinsDB";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "1111";

    public static void main(String[] args) {
        registerDriver();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO employees(name, phoneNumber) VALUES(?,?)");
            preparedStatement.setString(1, "Петров");
            preparedStatement.setString(2, "097-35436363");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM employees WHERE employees.Id = ?");
            preparedStatement.setInt(1, 2);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + "  " +
                        resultSet.getString("phoneNumber"));
            }


            preparedStatement = connection.prepareStatement(
                    "UPDATE employees SET name = \"Иванов\", phoneNumber = \"097-23453443\" WHERE employees.Id = ?");
            preparedStatement.setInt(1, 2);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(
                    "DELETE FROM employees WHERE employees.Id = ?");
            preparedStatement.setInt(1, 2);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private static void registerDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
