package service.impl;

import domain.Quote;

import java.util.Optional;

public interface QuoteServiceImpl {
    public Optional<Quote> save(Quote quote);
}
