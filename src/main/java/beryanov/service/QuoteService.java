package beryanov.service;

import beryanov.dto.ExtendedQuoteDto;
import beryanov.dto.QuoteDto;

import java.util.List;

public interface QuoteService {
    QuoteDto addQuote(QuoteDto quoteDto);
    List<ExtendedQuoteDto> getAllQuotes();
}
