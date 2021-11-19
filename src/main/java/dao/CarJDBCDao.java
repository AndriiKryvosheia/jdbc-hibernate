package dao;

import entity.Car;
import connection.Connector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Asus on 31.01.2018.
 */
public class CarJDBCDao implements IDAO<Car> {

    @Override
    public void add(Car car) {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            int markId = getMarkId(car.getMark(), connection);

            if (markId == -1) {
                statement = connection.prepareStatement("INSERT INTO marks(mark) VALUES (?)");
                statement.setString(1, car.getMark());

                statement.execute();

                statement = connection.prepareStatement("SELECT MAX(id) FROM marks");

                ResultSet rs = statement.executeQuery();

                rs.next();

                markId = rs.getInt(1);
            }

            statement = connection.prepareStatement("INSERT INTO cars(mark_id, model, price) VALUES (?, ?, ?)");

            statement.setInt(1, markId);
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getPrice());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, statement);
        }

    }

    private int getMarkId(String markName, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM marks WHERE mark = ? ");
            preparedStatement.setString(1, markName);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public List<Car> getAll() {
        List<Car> allCars = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT cars.id, marks.mark, cars.model, cars.price FROM cars " +
                    "INNER JOIN marks ON marks.id = cars.mark_id;");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Car car = new Car(
                        rs.getLong("id"),
                        rs.getString("mark"),
                        rs.getString("model"),
                        rs.getInt("price")
                );

                allCars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, statement);
        }

        return allCars;
    }

    @Override
    public Optional<Car> get(long id) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT m.mark, c.model, c.price FROM cars as c " +
                    "INNER JOIN marks as m ON m.id = c.mark_id WHERE c.id = ? ");

            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
//                String mark = rs.getString(1);
//                String model = rs.getString(2);
//                int price = rs.getInt(3);
//
//                Car car = new Car();
//
//                car.setId(id);
//                car.setModel(model);
//                car.setMark(mark);
//                car.setPrice(price);

                Car car = new Car(
                        id,
                        rs.getString("mark"),
                        rs.getString("model"),
                        rs.getInt("price")
                );

                return Optional.of(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement);
        }

        return Optional.empty();
    }

    @Override
    public void update(int carId, Car car) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("UPDATE cars SET mark_id = ?, model = ?, price = ? WHERE id = ?;");

            int markId = getMarkId(car.getMark(), connection);

            preparedStatement.setInt(1, markId);
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getPrice());

            preparedStatement.setLong(4, carId);

            int updatedValues = preparedStatement.executeUpdate();

            System.out.println("Values updated: " + updatedValues);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement);
        }
    }

    @Override
    public void remove(long id) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM cars WHERE id = ?;");

            preparedStatement.setLong(1, id);

            int updatedValues = preparedStatement.executeUpdate();

            System.out.println("Values deleted: " + updatedValues);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement);
        }
    }

    public int removeByMark(String mark) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        try {
            int markId = getMarkId(mark, connection);

            preparedStatement = connection.prepareStatement("DELETE FROM cars WHERE mark_id = ? ");

            preparedStatement.setInt(1, markId);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement);
        }

        return 0;
    }

    private void closeConnections(Connection connection, PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection getConnection() {
        Optional<Connection> optional = Connector.getConnection();

        if(optional.isEmpty()) {
            throw new RuntimeException("Wrong sql connection");
        }

        return optional.get();
    }
}
