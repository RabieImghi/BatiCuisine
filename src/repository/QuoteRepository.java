package repository;

import config.DatabaseConnection;
import domain.Project;
import domain.Quote;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Optional;

public class QuoteRepository {
    private Connection connection;
    public QuoteRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public Optional<Quote> save(Quote quote){
        try {
            this.connection.setAutoCommit(false);
            String query = "INSERT INTO quotes (estimated_amount , issue_date, validity_date, accepted,project_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement saveStmt = this.connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            saveStmt.setDouble(1, quote.getEstimatedAmount());
            saveStmt.setDate(2, Date.valueOf(quote.getIssueDate()));
            saveStmt.setDate(3, Date.valueOf(quote.getValidityDate()));
            saveStmt.setBoolean(4, quote.isAccepted());
            saveStmt.setInt(5, quote.getProject().getId());
            int results = saveStmt.executeUpdate();
            if(results > 0){
                int id = saveStmt.getGeneratedKeys().getInt(1);
                quote.setId(id);
                return Optional.of(quote);
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
    public Optional<Quote> getByIdProject(Project project){
        try {
            String query = "SELECT * FROM quotes WHERE project_id = ?";
            PreparedStatement getStmt = this.connection.prepareStatement(query);
            getStmt.setInt(1, project.getId());
            var result = getStmt.executeQuery();
            if(result.next()){
                Quote quote = new Quote(
                        result.getDouble("estimated_amount"),
                        result.getDate("issue_date").toLocalDate(),
                        result.getDate("validity_date").toLocalDate(),
                        result.getBoolean("accepted"),
                        project
                );
                quote.setId(result.getInt("id"));
                return Optional.of(quote);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
