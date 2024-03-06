package dev.patika.vetSystem.dao;

import dev.patika.vetSystem.entities.Animal;
import dev.patika.vetSystem.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {
}
