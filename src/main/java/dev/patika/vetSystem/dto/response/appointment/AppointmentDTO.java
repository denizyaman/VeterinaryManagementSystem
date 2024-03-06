package dev.patika.vetSystem.dto.response.appointment;

import dev.patika.vetSystem.entities.Animal;
import dev.patika.vetSystem.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private Long id;
    private LocalDateTime appointmentDate;
}
