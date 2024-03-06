package dev.patika.vetSystem.dto.response.appointment;

import dev.patika.vetSystem.dto.response.animal.AnimalDTO;
import dev.patika.vetSystem.dto.response.doctor.DoctorDTO;
import dev.patika.vetSystem.entities.Animal;
import dev.patika.vetSystem.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private Long id;
    private LocalDateTime appointmentDate;
    private DoctorDTO doctor;
    private AnimalDTO animal;
}
