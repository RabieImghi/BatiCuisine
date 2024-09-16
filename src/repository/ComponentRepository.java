package repository;

import config.DatabaseConnection;
import domain.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

public class ComponentRepository {
    private final Connection connection;
    public ComponentRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public Optional<Component> save(Component component){
        try {
            this.connection.setAutoCommit(false);
            String stmt = "INSERT INTO components (name, component_type, vat_rate, project_id) VALUES (?,?,?,?)";
            PreparedStatement saveStmt = this.connection.prepareStatement(stmt, PreparedStatement.RETURN_GENERATED_KEYS );
            saveStmt.setString(1, component.getName());
            saveStmt.setString(2, component.getComponentType());
            saveStmt.setDouble(3, component.getVatRate());
            saveStmt.setInt(4, component.getProject().getId());
            int results = saveStmt.executeUpdate();
            if(results > 0){
                int id = saveStmt.getGeneratedKeys().getInt(1);
                component.setId(id);
                return Optional.of(component);
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
