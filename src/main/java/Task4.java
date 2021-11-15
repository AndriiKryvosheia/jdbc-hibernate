import java.sql.*;

public class Task4 {

    private static final String URL = "jdbc:mysql://localhost:3306/MyJoinsDB";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "1111";


    public static void main(String[] args) {
        registerDriver();

        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Correct connection to db!");
            }
            statement = connection.createStatement();

            System.out.println("\n -----task 4.1-----");
            ResultSet resultSet = statement.executeQuery(
                    "select\n" +
                            "employees.name,\n" +
                            "employees.phoneNumber,\n" +
                            "employeeData.adress\n" +
                            "from\n" +
                            "employees\n" +
                            "left join employeeData\n" +
                            "on employees.ID = employeeData.employeeId");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + "  " +
                        resultSet.getString("phoneNumber") + "  " +
                        resultSet.getString("adress"));
            }

            System.out.println("\n -----task 4.2-----");
            resultSet = statement.executeQuery(
                    "select\n" +
                            "employees.name,\n" +
                            "employees.phoneNumber,\n" +
                            "employeeData.birthday\n" +
                            "from\n" +
                            "employees\n" +
                            "join employeeData\n" +
                            "on employees.ID = employeeData.employeeId\n" +
                            "where\n" +
                            "employeeData.maritalStatus = 'не женат'");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + "  " +
                        resultSet.getString("phoneNumber") + "  " +
                        resultSet.getString("birthday"));
            }

            System.out.println("\n -----task 4.3-----");
            resultSet = statement.executeQuery(
                    "select\n" +
                            "employees.name,\n" +
                            "employees.phoneNumber,\n" +
                            "employeeData.birthday\n" +
                            "from\n" +
                            "employees\n" +
                            "join salary\n" +
                            "on employees.ID = salary.employeeId\n" +
                            "left join employeeData\n" +
                            "on employees.ID = employeeData.employeeId\n" +
                            "where\n" +
                            "salary.position = 'Менеджер'");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + "  " +
                        resultSet.getString("phoneNumber") + "  " +
                        resultSet.getString("birthday"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            try {
                connection.close();
                statement.close();
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
