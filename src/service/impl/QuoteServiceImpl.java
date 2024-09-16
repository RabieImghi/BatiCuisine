package service.impl;

import domain.Project;
import domain.Quote;

import java.util.Optional;

public interface QuoteServiceImpl {
    public Optional<Quote> save(Quote quote);

    public Optional<Quote> getByIdProject(Project project);
}
