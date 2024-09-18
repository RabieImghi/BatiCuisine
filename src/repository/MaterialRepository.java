package repository;

import config.DatabaseConnection;
import domain.Material;
import domain.Project;
import repository.impl.MaterialRepositoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaterialRepository implements MaterialRepositoryImpl {
    private final Connection connection;

    public MaterialRepository(){
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
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
    @Override
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
                material.setIdMaterial(results.getInt("id"));
                listMaterials.add(material);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listMaterials;
    }
    @Override
    public void updateVAT(Project project, double vatRate){
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

    @Override
    public Optional<Material> getById(int id){
        try {
            String stmt = "SELECT * FROM materials WHERE id = ?";
            PreparedStatement getStmt = this.connection.prepareStatement(stmt);
            getStmt.setInt(1, id);
            ResultSet results = getStmt.executeQuery();
            if(results.next()){
                Project project = new Project();
                project.setId(results.getInt("project_id"));
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
                return Optional.of(material);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Material material){
        try {
            this.connection.setAutoCommit(false);
            String stmUpdate = "UPDATE materials SET name = ?, component_type = ?, vat_rate = ?, project_id = ?, unit_cost = ?, quantity = ?, transport_cost = ?, quality_coefficient = ? WHERE id = ?";
            PreparedStatement save = this.connection.prepareStatement(stmUpdate);
            save.setString(1,material.getName());
            save.setString(2,material.getComponentType());
            save.setDouble(3,material.getVatRate());
            save.setInt(4,material.getProject().getId());
            save.setDouble(5,material.getUnitCost());
            save.setDouble(6,material.getQuantity());
            save.setDouble(7,material.getTransportCost());
            save.setDouble(8,material.getQualityCoefficient());
            save.setInt(9,material.getId());
            save.executeUpdate();
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
