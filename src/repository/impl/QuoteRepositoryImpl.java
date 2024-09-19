package repository.impl;

import domain.Project;
import domain.Quote;

import java.util.List;
import java.util.Optional;

public interface QuoteRepositoryImpl {
    public Optional<Quote> save(Quote quote);

    public Optional<Quote> getByIdProject(Project project);
    public List<Quote> getAll();
    public Optional<Quote> update(Quote quote);
    public Optional<Quote> delete(Quote quote);

}
