package repository;

import config.DatabaseConnection;
import domain.Labor;
import domain.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LaborRepository {
    private Connection connection;
    public LaborRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

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
                int id = saveStmt.getGeneratedKeys().getInt(1);
                labor.setIdLabor(id);
                return Optional.of(labor);
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

}
