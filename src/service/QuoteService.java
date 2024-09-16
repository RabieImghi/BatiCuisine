package service;

import domain.Project;
import domain.Quote;
import repository.QuoteRepository;
import service.impl.QuoteServiceImpl;

import java.util.Optional;

public class QuoteService implements QuoteServiceImpl {
    private final QuoteRepository quoteRepository = new QuoteRepository();
    public Optional<Quote> save(Quote quote) {
        return quoteRepository.save(quote);
    }
    public Optional<Quote> getByIdProject(Project project) {
        return quoteRepository.getByIdProject(project);
    }
}
