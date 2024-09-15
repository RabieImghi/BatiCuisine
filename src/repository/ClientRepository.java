package repository;

import config.DatabaseConnection;
import domain.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository {
    private final Connection connection;
    public ClientRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    public Optional<Client> save(Client client){
        Optional<Client> clientOptional = Optional.empty();
        try {
            String smt = "INSERT INTO clients (name, address, phone, is_professional) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(smt);
            preparedStatement.setString(1,client.getName());
            preparedStatement.setString(2,client.getAddress());
            preparedStatement.setString(3,client.getPhone());
            preparedStatement.setBoolean(4,client.isProfessional());
            int rowsInserted = preparedStatement.executeUpdate();
            if(rowsInserted > 0) {
                clientOptional = Optional.of(client);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return clientOptional;
    }
    public Optional<Client> update(Client client){
        try{
            connection.setAutoCommit(false);
            String stm = "UPDATE clients SET name = ?, address = ?, phone = ?, is_professional = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(stm);
            preparedStatement.setString(1,client.getName());
            preparedStatement.setString(2,client.getAddress());
            preparedStatement.setString(3,client.getPhone());
            preparedStatement.setBoolean(4,client.isProfessional());
            preparedStatement.setInt(5,client.getId());
            int result = preparedStatement.executeUpdate();
            if(result > 0) return Optional.of(client);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
    public Optional<Client> delete(Client client){
        try{
            this.connection.setAutoCommit(false);
            String stm = "DELETE FROM clients WHERE id = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(stm);
            preparedStatement.setInt(1,client.getId());
            int res = preparedStatement.executeUpdate();
            if(res > 0) return Optional.of(client);
        }catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                this.connection.setAutoCommit(true);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
    public Optional<Client> findById(int id){
        Optional<Client> clientOptional = Optional.empty();
        try {
            String stm2 = "SELECT * FROM clients WHERE id = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(stm2);
            preparedStatement2.setInt(1, id);
            ResultSet resultSet = preparedStatement2.executeQuery();
            while (resultSet.next()){
                clientOptional = Optional.of(new Client(resultSet.getString("name"),resultSet.getString("address"),resultSet.getString("phone"),resultSet.getBoolean("is_professional")));
                clientOptional.get().setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientOptional;
    }
    public Optional<Client> findByName(String name){
        Optional<Client> clientOptional = Optional.empty();
        try {
            String stm2 = "SELECT * FROM clients WHERE name = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(stm2);
            preparedStatement2.setString(1, name);
            ResultSet resultSet = preparedStatement2.executeQuery();
            while (resultSet.next()){
                clientOptional = Optional.of(new Client(resultSet.getString("name"),resultSet.getString("address"),resultSet.getString("phone"),resultSet.getBoolean("is_professional")));
                clientOptional.get().setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientOptional;
    }
    public List<Client> getAll(){
        List<Client> clients = new ArrayList<>();
        try {
            String stm = "SELECT * FROM clients";
            PreparedStatement preparedStatement = connection.prepareStatement(stm);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Client client = new Client(resultSet.getString("name"),resultSet.getString("address"),resultSet.getString("phone"),resultSet.getBoolean("is_professional"));
                client.setId(resultSet.getInt("id"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
