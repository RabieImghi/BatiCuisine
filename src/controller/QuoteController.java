package controller;

import domain.Quote;
import service.QuoteService;

import java.util.Optional;

public class QuoteController {
    private final QuoteService quoteService = new QuoteService();
    public Optional<Quote> save(Quote quote){
        return quoteService.save(quote);
    }
}
