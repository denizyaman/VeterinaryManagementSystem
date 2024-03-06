package dev.patika.vetSystem.dto.response.animal;

import dev.patika.vetSystem.dto.response.appointment.AppointmentDTO;
import dev.patika.vetSystem.dto.response.customer.CustomerDTO;
import dev.patika.vetSystem.dto.response.vaccine.VaccineDTO;
import dev.patika.vetSystem.entities.Appointment;
import dev.patika.vetSystem.entities.Customer;
import dev.patika.vetSystem.entities.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
public class AnimalResponse {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private String gender;
    private String colour;
    private LocalDate dateOfBirth;
    private CustomerDTO customer;
    private List<VaccineDTO> vaccineList;
    private List<AppointmentDTO> appointmentList;
}
