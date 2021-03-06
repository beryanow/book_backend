package beryanov.service;

import beryanov.dto.ChangeContentDto;
import beryanov.dto.CritiqueDto;

import java.util.List;

public interface CritiqueService {
    CritiqueDto addCritique(CritiqueDto critiqueDto);
    List<CritiqueDto> getAllCritiques();
    CritiqueDto editCritique(ChangeContentDto changeContentDto);
    void removeCritique(String critiqueId);
}
