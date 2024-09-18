package repository;

import config.DatabaseConnection;
import domain.Material;
import domain.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaterialRepository {
    private final Connection connection;

    public MaterialRepository(){
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public Optional<Material> save(Material material){
        try {
            this.connection.setAutoCommit(false);
            String stmt = "INSERT INTO materials (name, component_type, vat_rate, project_id, unit_cost, quantity, transport_cost, quality_coefficient) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement saveStmt = this.connection.prepareStatement(stmt, PreparedStatement.RETURN_GENERATED_KEYS );
            saveStmt.setString(1, material.getName());
            saveStmt.setString(2, material.getComponentType());
            saveStmt.setDouble(3, material.getVatRate());
            saveStmt.setInt(4, material.getProject().getId());
            saveStmt.setDouble(5, material.getUnitCost());
            saveStmt.setDouble(6, material.getQuantity());
            saveStmt.setDouble(7, material.getTransportCost());
            saveStmt.setDouble(8, material.getQualityCoefficient());
            int results = saveStmt.executeUpdate();

            if(results > 0){
                ResultSet generatedKeys = saveStmt.getGeneratedKeys();
                if(generatedKeys.next()){
                    int id = generatedKeys.getInt(1);
                    material.setId(id);
                    return Optional.of(material);
                }
            }
        }catch (Exception e){
            try {
                this.connection.rollback();
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }finally {
            try {
                this.connection.setAutoCommit(true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
    public List<Material> getAll(Project project){
        List<Material> listMaterials = new ArrayList<>();
        try {
            String stmt = "SELECT * FROM materials WHERE project_id = ?";
            PreparedStatement getStmt = this.connection.prepareStatement(stmt);
            getStmt.setInt(1, project.getId());
            ResultSet results = getStmt.executeQuery();
            while (results.next()){
                Material material = new Material(
                        results.getString("name"),
                        results.getString("component_type"),
                        results.getDouble("vat_rate"),
                        results.getDouble("unit_cost"),
                        results.getDouble("quantity"),
                        results.getDouble("transport_cost"),
                        results.getDouble("quality_coefficient"),
                        project);
                material.setId(results.getInt("id"));
                listMaterials.add(material);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listMaterials;
    }
    public void updateVAT(Project project,double vatRate){
        try {
            this.connection.setAutoCommit(false);
            String stmUpdate = "UPDATE components SET vat_rate = ? WHERE project_id = ?";
            PreparedStatement save = this.connection.prepareStatement(stmUpdate);
            save.setDouble(1,vatRate);
            save.setInt(2,project.getId());
            int res = save.executeUpdate();
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
