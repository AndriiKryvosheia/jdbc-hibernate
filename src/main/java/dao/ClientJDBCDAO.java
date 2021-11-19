package dao;

import connection.Connector;
import entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientJDBCDAO implements IDAO<Client> {

    @Override
    public void add(Client element) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO clients(name, age, phone) VALUES (?, ?, ?)");
            statement.setString(1, element.getName());
            statement.setInt(2, element.getAge());
            statement.setString(3, element.getPhone());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, statement);
        }
    }

    @Override
    public List<Client> getAll() {
        List<Client> allClients = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                    "SELECT clients.id, clients.name, clients.age, clients.phone FROM clients;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Client client = new Client(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("phone")
                );
                allClients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, statement);
        }

        return allClients;
    }

    @Override
    public Optional<Client> get(long id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                    "SELECT clients.id, clients.name, clients.age, clients.phone FROM clients" +
                            " WHERE id = ?;");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Client client = new Client(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("phone")
                );
                return Optional.of(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, statement);
        }

        return Optional.empty();
    }

    @Override
    public void update(int id, Client element) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE clients SET name = ?, age = ?, phone = ? WHERE id = ?;");
            preparedStatement.setString(1, element.getName());
            preparedStatement.setInt(2, element.getAge());
            preparedStatement.setString(3, element.getPhone());
            preparedStatement.setInt(4, id);
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
            preparedStatement = connection.prepareStatement("DELETE FROM clients WHERE id = ?;");
            preparedStatement.setLong(1, id);
            int updatedValues = preparedStatement.executeUpdate();
            System.out.println("Values deleted: " + updatedValues);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement);
        }
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
