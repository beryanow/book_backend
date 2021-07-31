package beryanov.controller;

import beryanov.dto.ExtendedQuoteDto;
import beryanov.dto.QuoteDto;
import beryanov.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/quote")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public QuoteDto addQuote(@Valid @RequestBody QuoteDto quoteDto) {
        return quoteService.addQuote(quoteDto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExtendedQuoteDto> getAllQuotes() {
        return quoteService.getAllQuotes();
    }
}
