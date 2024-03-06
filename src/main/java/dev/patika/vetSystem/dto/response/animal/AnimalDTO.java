package dev.patika.vetSystem.dto.response.animal;

import dev.patika.vetSystem.dto.response.customer.CustomerDTO;
import dev.patika.vetSystem.dto.response.vaccine.VaccineDTO;
import dev.patika.vetSystem.entities.Appointment;
import dev.patika.vetSystem.entities.Customer;
import dev.patika.vetSystem.entities.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
public class AnimalDTO {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private String gender;
    private String colour;
    private LocalDate dateOfBirth;
}
