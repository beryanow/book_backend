package beryanov.controller;

import beryanov.dto.ChangeContentDto;
import beryanov.dto.CritiqueDto;
import beryanov.dto.ObjectIdHolder;
import beryanov.service.CritiqueService;
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
@RequestMapping(path = "/critique")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CritiqueController {
    private final CritiqueService critiqueService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CritiqueDto addCritique(@Valid @RequestBody CritiqueDto critiqueDto) {
        return critiqueService.addCritique(critiqueDto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CritiqueDto> getAllCritiques() {
        return critiqueService.getAllCritiques();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    public CritiqueDto editCritique(@Valid @RequestBody ChangeContentDto changeContentDto) {
        return critiqueService.editCritique(changeContentDto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeCritique(@Valid @RequestBody ObjectIdHolder objectIdHolder) {
        critiqueService.removeCritique(objectIdHolder.getId());
    }
}
