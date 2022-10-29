package beryanov.controller;

import beryanov.dto.StateDto;
import beryanov.service.StateService;
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

@RestController
@RequestMapping(path = "/state")
@CrossOrigin(origins = {"http://localhost:3000", "http://5.53.124.106:8080"})
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void changeState(@Valid @RequestBody StateDto stateDto) {
        stateService.changeState(stateDto);
    }
}
