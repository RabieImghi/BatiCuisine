package repository;

import config.DatabaseConnection;
import domain.Client;
import domain.Project;
import repository.impl.ProjectRepositoryImpl;
import utils.ProjectStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectRepository implements ProjectRepositoryImpl {
    public Connection connection;
    public ProjectRepository(){
        connection = DatabaseConnection.getInstance().getConnection();
    }
    @Override
    public Optional<Project> save(Project project){
        try {
            this.connection.setAutoCommit(false);
            String query = "INSERT INTO projects (project_name, profit_margin, total_cost,project_status,client_id)" +
                    "VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,project.getProjectName());
            preparedStatement.setDouble(2,project.getProfitMargin());
            preparedStatement.setDouble(3,project.getTotalCost());
            preparedStatement.setString(4,String.valueOf(project.getProjectStatus()));
            preparedStatement.setInt(5,project.getClient().getId());
            int resultOfInsert = preparedStatement.executeUpdate();
            if(resultOfInsert > 0 ){
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next()){
                    project.setId(generatedKeys.getInt(1));
                    return  Optional.of(project);
                }
            }
        }catch (SQLException e){
            try {
                this.connection.rollback();
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }finally {
            try {
                this.connection.setAutoCommit(true);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
    @Override
    public void updateProfitMargin(Project project, double profitMargin){
        try {
            this.connection.setAutoCommit(false);
            String update = "UPDATE projects SET profit_margin = ? WHERE id = ?";
            PreparedStatement updateStm = this.connection.prepareStatement(update);
            updateStm.setDouble(1,profitMargin);
            updateStm.setInt(2,project.getId());
            updateStm.executeUpdate();
        }catch (SQLException e){
            try {
                this.connection.rollback();
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }finally {
            try {
                this.connection.setAutoCommit(true);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void updateProfitCost(Project project, double totalCost){
        try {
            this.connection.setAutoCommit(false);
            String update = "UPDATE projects SET total_cost = ? WHERE id = ?";
            PreparedStatement updateStm = this.connection.prepareStatement(update);
            updateStm.setDouble(1,totalCost);
            updateStm.setInt(2,project.getId());
            updateStm.executeUpdate();
        }catch (SQLException e){
            try {
                this.connection.rollback();
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }finally {
            try {
                this.connection.setAutoCommit(true);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Project> getAll(){
        List<Project> projects = new ArrayList<>();
        try {
            String query = "SELECT * FROM projects INNER JOIN clients on clients.id = projects.client_id";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Client client = new Client(
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("is_professional"));

                Project project = new Project(resultSet.getString("project_name"),client);
                project.setId(resultSet.getInt("id"));
                project.setProfitMargin(resultSet.getDouble("profit_margin"));
                project.setTotalCost(resultSet.getDouble("total_cost"));
                project.setProjectStatus(ProjectStatus.valueOf(resultSet.getString("project_status")));
                projects.add(project);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public Optional<Project> getById(int id){
        try {
            String query = "SELECT * FROM projects INNER JOIN clients on clients.id = projects.client_id WHERE projects.id = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Client client = new Client(
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("is_professional"));
                client.setId(resultSet.getInt("client_id"));

                Project project = new Project(resultSet.getString("project_name"),client);
                project.setId(resultSet.getInt("id"));
                project.setProfitMargin(resultSet.getDouble("profit_margin"));
                project.setTotalCost(resultSet.getDouble("total_cost"));
                project.setProjectStatus(ProjectStatus.valueOf(resultSet.getString("project_status")));
                return Optional.of(project);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public void delete(Project project){
        try {
            this.connection.setAutoCommit(false);
            String query = "DELETE FROM projects WHERE id = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1,project.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            try {
                this.connection.rollback();
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }finally {
            try {
                this.connection.setAutoCommit(true);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void update(Project project){
        try {
            this.connection.setAutoCommit(false);
            String query = "UPDATE projects SET project_name = ?, profit_margin = ?, total_cost = ?, project_status = ?, client_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,project.getProjectName());
            preparedStatement.setDouble(2,project.getProfitMargin());
            preparedStatement.setDouble(3,project.getTotalCost());
            preparedStatement.setString(4,String.valueOf(project.getProjectStatus()));
            preparedStatement.setInt(5,project.getClient().getId());
            preparedStatement.setInt(6,project.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            try {
                this.connection.rollback();
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }finally {
            try {
                this.connection.setAutoCommit(true);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
