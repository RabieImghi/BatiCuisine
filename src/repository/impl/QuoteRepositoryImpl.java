package repository.impl;

import domain.Project;
import domain.Quote;

import java.util.Optional;

public interface QuoteRepositoryImpl {
    Optional<Quote> save(Quote quote);

    Optional<Quote> getByIdProject(Project project);
}
