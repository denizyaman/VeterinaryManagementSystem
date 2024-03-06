package dev.patika.vetSystem.business.abstracts;

import dev.patika.vetSystem.core.result.Result;
import dev.patika.vetSystem.core.result.ResultData;
import dev.patika.vetSystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.vetSystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.vetSystem.dto.response.CursorResponse;
import dev.patika.vetSystem.dto.response.appointment.AppointmentResponse;
import dev.patika.vetSystem.entities.Appointment;
import dev.patika.vetSystem.entities.Doctor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    ResultData<AppointmentResponse> save(AppointmentSaveRequest appointmentSaveRequest);
    ResultData<AppointmentResponse> get(Long id);
    ResultData<CursorResponse<AppointmentResponse>> cursor(int page, int pageSize);
    ResultData<AppointmentResponse> update(AppointmentUpdateRequest appointmentUpdateRequest);

    List<AppointmentResponse> getAppointmentsByDateRangeAndDoctorId(LocalDate startDate, LocalDate endDate, Long doctorId);
    List<AppointmentResponse>  getAppointmentsByDateRangeAndAnimalId(LocalDate startDate, LocalDate endDate, Long animalId);
    Result delete (Long id);
    boolean isAvailable(LocalDateTime date, Doctor doctor);
}
