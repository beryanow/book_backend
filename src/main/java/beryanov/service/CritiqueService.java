package beryanov.service;

import beryanov.dto.CritiqueDto;

import java.util.List;

public interface CritiqueService {
    CritiqueDto addCritique(CritiqueDto critiqueDto);
    List<CritiqueDto> getAllCritiques();
    CritiqueDto editCritique(CritiqueDto critiqueDto);
    void removeCritique(String critiqueId);
}
