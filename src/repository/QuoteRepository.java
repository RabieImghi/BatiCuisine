package repository;

import config.DatabaseConnection;
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
}
