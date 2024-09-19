package service;

import domain.Project;
import domain.Quote;
import repository.QuoteRepository;
import service.impl.QuoteServiceImpl;

import java.util.List;
import java.util.Optional;

public class QuoteService implements QuoteServiceImpl {
    private final ProjectService projectService = new ProjectService();
    private final QuoteRepository quoteRepository = new QuoteRepository();
    public Optional<Quote> save(Quote quote) {
        return quoteRepository.save(quote);
    }
    public Optional<Quote> getByIdProject(Project project) {
        return quoteRepository.getByIdProject(project);
    }
    public List<Quote> getAll() {
        List<Quote> quotes = quoteRepository.getAll();
        for (Quote quote1 : quotes) {
            Optional<Project> project = projectService.getById(quote1.getProject().getId());
            project.ifPresent(quote1::setProject);
        }
        return  quotes;
    }
    public Optional<Quote> update(Quote quote) {
        return quoteRepository.update(quote);
    }
    public Optional<Quote> delete(Quote quote) {
        return quoteRepository.delete(quote);
    }
}
