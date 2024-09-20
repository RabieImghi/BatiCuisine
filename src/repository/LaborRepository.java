package repository;

import config.DatabaseConnection;
import domain.Labor;
import domain.Project;
import repository.impl.LaborRepositoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LaborRepository implements LaborRepositoryImpl {
    private final Connection connection;
    public LaborRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public Optional<Labor> save(Labor labor) {
        try {
            this.connection.setAutoCommit(false);
            String stmLabor = "INSERT INTO labors " +
                    "(name, component_type, vat_rate, project_id,hourly_rate,hours_worked,worker_productivity) " +
                    "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement saveStmt = this.connection.prepareStatement(stmLabor, PreparedStatement.RETURN_GENERATED_KEYS);
            saveStmt.setString(1, labor.getName());
            saveStmt.setString(2, labor.getComponentType());
            saveStmt.setDouble(3, labor.getVatRate());
            saveStmt.setInt(4, labor.getProject().getId());
            saveStmt.setDouble(5, labor.getHourlyRate());
            saveStmt.setDouble(6, labor.getHoursWorked());
            saveStmt.setDouble(7, labor.getWorkerProductivity());
            int results = saveStmt.executeUpdate();
            if (results > 0) {
                ResultSet generatedKeys = saveStmt.getGeneratedKeys();
                if(generatedKeys.next()){
                    int id = generatedKeys.getInt(1);
                    labor.setIdLabor(id);
                    return Optional.of(labor);
                }
            }
        } catch (Exception e) {
            try {
                this.connection.rollback();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        } finally {
            try {
                this.connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Labor> getAll(Project project) {
        List<Labor> labors = new ArrayList<>();
        try {
            String stmLabor = "SELECT * FROM labors WHERE project_id = ?";
            PreparedStatement getStmt = this.connection.prepareStatement(stmLabor);
            getStmt.setInt(1, project.getId());
            ResultSet rs = getStmt.executeQuery();
            while (rs.next()) {
                Labor labor = new Labor(
                        rs.getString("name"),
                        rs.getString("component_type"),
                        rs.getDouble("vat_rate"),
                        rs.getDouble("hourly_rate"),
                        rs.getDouble("hours_worked"),
                        rs.getDouble("worker_productivity"),
                        project
                );
                labor.setIdLabor(rs.getInt("id"));
                labors.add(labor);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return labors;
    }

    @Override
    public Optional<Labor> getById(int id) {
        try {
            String stmLabor = "SELECT * FROM labors WHERE id = ?";
            PreparedStatement getStmt = this.connection.prepareStatement(stmLabor);
            getStmt.setInt(1, id);
            ResultSet rs = getStmt.executeQuery();
            if (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("project_id"));
                Labor labor = new Labor(
                        rs.getString("name"),
                        rs.getString("component_type"),
                        rs.getDouble("vat_rate"),
                        rs.getDouble("hourly_rate"),
                        rs.getDouble("hours_worked"),
                        rs.getDouble("worker_productivity"),
                        project
                );
                labor.setIdLabor(rs.getInt("id"));
                return Optional.of(labor);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void delete(Labor labor) {
        try {
            this.connection.setAutoCommit(false);
            String stmLabor = "DELETE FROM labors WHERE id = ?";
            PreparedStatement deleteStmt = this.connection.prepareStatement(stmLabor);
            deleteStmt.setInt(1, labor.getIdLabor());
            deleteStmt.executeUpdate();
        } catch (Exception e) {
            try {
                this.connection.rollback();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        } finally {
            try {
                this.connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Labor labor) {
        try {
            this.connection.setAutoCommit(false);
            String stmLabor = "UPDATE labors SET name = ?, component_type = ?, vat_rate = ?, hourly_rate = ?, hours_worked = ?, worker_productivity = ? WHERE id = ?";
            PreparedStatement updateStmt = this.connection.prepareStatement(stmLabor);
            updateStmt.setString(1, labor.getName());
            updateStmt.setString(2, labor.getComponentType());
            updateStmt.setDouble(3, labor.getVatRate());
            updateStmt.setDouble(4, labor.getHourlyRate());
            updateStmt.setDouble(5, labor.getHoursWorked());
            updateStmt.setDouble(6, labor.getWorkerProductivity());
            updateStmt.setInt(7, labor.getIdLabor());
            updateStmt.executeUpdate();
        } catch (Exception e) {
            try {
                this.connection.rollback();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        } finally {
            try {
                this.connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
