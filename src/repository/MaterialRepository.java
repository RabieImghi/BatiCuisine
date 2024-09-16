package repository;

import config.DatabaseConnection;
import domain.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
                int id = saveStmt.getGeneratedKeys().getInt(1);
                material.setId(id);
                return Optional.of(material);
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
}
