package dev.patika.vetSystem.dto.request.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {
    @NotNull(message = "Kategori Adı Boş veya Null Olamaz")
    private Long id;
    @NotNull(message = "Randevu Tarihi Boş veya Null Olamaz")
    private LocalDateTime appointmentDate;
    @NotNull
    private Long animal_id;
    @NotNull
    private Long doctor_id;
}
