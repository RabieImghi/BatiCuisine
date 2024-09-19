package repository;

import config.DatabaseConnection;
import domain.Labor;
import domain.Project;
import domain.Quote;
import repository.impl.QuoteRepositoryImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuoteRepository implements QuoteRepositoryImpl {
    private final Connection connection;
    public QuoteRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    @Override
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
                ResultSet generatedKeys = saveStmt.getGeneratedKeys();
                if(generatedKeys.next()){
                    int id = generatedKeys.getInt(1);
                    quote.setId(id);
                    return Optional.of(quote);
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

    public List<Quote> getAll(){
        List<Quote> quoteList = new ArrayList<>();
        try {
            String query = "SELECT * FROM quotes";
            PreparedStatement getStmt = this.connection.prepareStatement(query);
            ResultSet result = getStmt.executeQuery();
            while(result.next()){
                Project project = new Project();
                project.setId(result.getInt("project_id"));
                Quote quote1 = new Quote(
                        result.getDouble("estimated_amount"),
                        result.getDate("issue_date").toLocalDate(),
                        result.getDate("validity_date").toLocalDate(),
                        result.getBoolean("accepted"),
                        project
                );
                quote1.setId(result.getInt("id"));
                quoteList.add(quote1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return quoteList;
    }
    public Optional<Quote> update(Quote quote){
        try {
            this.connection.setAutoCommit(false);
            String query = "UPDATE quotes SET estimated_amount = ?, issue_date = ?, validity_date = ?, accepted = ? WHERE id = ?";
            PreparedStatement updateStmt = this.connection.prepareStatement(query);
            updateStmt.setDouble(1, quote.getEstimatedAmount());
            updateStmt.setDate(2, Date.valueOf(quote.getIssueDate()));
            updateStmt.setDate(3, Date.valueOf(quote.getValidityDate()));
            updateStmt.setBoolean(4, quote.isAccepted());
            updateStmt.setInt(5, quote.getId());
            int results = updateStmt.executeUpdate();
            if(results > 0){
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
    public Optional<Quote> delete(Quote quote){
        try {
            this.connection.setAutoCommit(false);
            String query = "DELETE FROM quotes WHERE id = ?";
            PreparedStatement deleteStmt = this.connection.prepareStatement(query);
            deleteStmt.setInt(1, quote.getId());
            int results = deleteStmt.executeUpdate();
            if(results > 0){
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
