package dev.patika.vetSystem.dao;

import dev.patika.vetSystem.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine,Long> {

    Optional<Vaccine> findByAnimalIdAndName(Long id, String name);
}
