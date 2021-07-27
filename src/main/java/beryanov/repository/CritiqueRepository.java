package beryanov.repository;

import beryanov.model.Critique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CritiqueRepository extends JpaRepository<Critique, String> {}
