package dev.patika.vetSystem.dto.response.vaccine;


import dev.patika.vetSystem.dto.response.animal.AnimalDTO;
import dev.patika.vetSystem.entities.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineResponse {
    private Long id;
    private String name;
    private String code;
    private LocalDate protectionStartDate;
    private LocalDate protectionFinishDate;
    private AnimalDTO animal;
}
