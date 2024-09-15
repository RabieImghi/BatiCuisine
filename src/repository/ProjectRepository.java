package repository;

import config.DatabaseConnection;
import domain.Client;
import domain.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProjectRepository {
    public Connection connection;
    public ProjectRepository(){
        connection = DatabaseConnection.getInstance().getConnection();
    }
    public Optional<Project> save(Project project, Client client){
        try {
            this.connection.setAutoCommit(false);
            String query = "INSERT INTO projects (project_name, profit_margin, total_cost,project_status,client_id)" +
                    "VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,project.getProjectName());
            preparedStatement.setDouble(2,project.getProfitMargin());
            preparedStatement.setDouble(3,project.getTotalCost());
            preparedStatement.setString(4,String.valueOf(project.getProjectStatus()));
            preparedStatement.setInt(5,client.getId());
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
}
