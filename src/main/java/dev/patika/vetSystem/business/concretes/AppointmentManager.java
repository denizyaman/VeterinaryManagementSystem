package dev.patika.vetSystem.business.concretes;

import dev.patika.vetSystem.business.abstracts.IAppointmentService;
import dev.patika.vetSystem.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetSystem.core.exception.NotFoundException;
import dev.patika.vetSystem.core.result.Result;
import dev.patika.vetSystem.core.result.ResultData;
import dev.patika.vetSystem.core.utilites.Msg;
import dev.patika.vetSystem.core.utilites.ResultHelper;
import dev.patika.vetSystem.dao.AnimalRepo;
import dev.patika.vetSystem.dao.AppointmentRepo;
import dev.patika.vetSystem.dao.AvailableDateRepo;
import dev.patika.vetSystem.dao.DoctorRepo;
import dev.patika.vetSystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.vetSystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.vetSystem.dto.response.CursorResponse;
import dev.patika.vetSystem.dto.response.animal.AnimalResponse;
import dev.patika.vetSystem.dto.response.appointment.AppointmentResponse;
import dev.patika.vetSystem.entities.Animal;
import dev.patika.vetSystem.entities.Appointment;
import dev.patika.vetSystem.entities.AvailableDate;
import dev.patika.vetSystem.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentManager implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final AnimalRepo animalRepo;
    private final AvailableDateRepo availableDateRepo;
    private final IModelMapperService modelMapper;
    private final DoctorRepo doctorRepo;

    public AppointmentManager(AvailableDateRepo availableDateRepo,AppointmentRepo appointmentRepo, AnimalRepo animalRepo, IModelMapperService modelMapper, DoctorRepo doctorRepo) {
        this.appointmentRepo = appointmentRepo;
        this.animalRepo = animalRepo;
        this.modelMapper = modelMapper;
        this.doctorRepo = doctorRepo;
        this.availableDateRepo=availableDateRepo;
    }

    @Override
    public ResultData<AppointmentResponse> save(AppointmentSaveRequest appointmentSaveRequest) {
        Appointment saveAppointment= this.modelMapper.forRequest().map(appointmentSaveRequest, Appointment.class);
        Doctor doctor=doctorRepo.findById(appointmentSaveRequest.getDoctor_id()).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
        Animal animal=animalRepo.findById(appointmentSaveRequest.getAnimal_id()).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));

        // 1 saat aralıklı randevu alınabılır kontrolu sadece saat başları için
        if (saveAppointment.getAppointmentDate().getMinute() != 0 || saveAppointment.getAppointmentDate().getSecond() != 0) {
            throw new NotFoundException(Msg.ONLY_HOURLY);
        }

        // Doktorun müsait günü var mı kontrolü
        if (!this.isAvailable(saveAppointment.getAppointmentDate(), doctor)) {
            throw new NotFoundException(Msg.DOCTOR_BUSY);
        }

        // Doktorun dirilen saatte başka bir randevusu var mı kontrolü
        if (this.hasAppointmentAtGivenTime(saveAppointment.getAppointmentDate(), doctor)) {
            throw new NotFoundException(Msg.APPOINTMENT_BUSY);
        }

        saveAppointment.setDoctor(doctor);
        saveAppointment.setAnimal(animal);
        this.appointmentRepo.save(saveAppointment);

        return ResultHelper.created(this.modelMapper.forResponse().map(saveAppointment, AppointmentResponse.class));
    }

    @Override
    public ResultData<AppointmentResponse> get(Long id) {
        Appointment appointment=appointmentRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
        return ResultHelper.success(this.modelMapper.forResponse().map(appointment,AppointmentResponse.class));    }

    @Override
    public ResultData<CursorResponse<AppointmentResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Appointment> appointmentPage=appointmentRepo.findAll(pageable);
        Page<AppointmentResponse> appointmentResponsePage = appointmentPage
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class));
        return ResultHelper.cursor(appointmentResponsePage);
    }

    @Override
    public ResultData<AppointmentResponse> update(AppointmentUpdateRequest appointmentUpdateRequest) {
        Appointment updateAppointment= this.modelMapper.forRequest().map(appointmentUpdateRequest,Appointment.class);
        this.get(appointmentUpdateRequest.getId());
        this.appointmentRepo.save(updateAppointment);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAppointment,AppointmentResponse.class));
    }


    @Override
    public Result delete(Long id) {
        ResultData<AppointmentResponse> appointment = this.get(id);
        Appointment appointment1=this.modelMapper.forResponse().map(appointment.getData(), Appointment.class);
        this.appointmentRepo.delete(appointment1);
        return ResultHelper.ok();
    }

    @Override
    public boolean isAvailable(LocalDateTime date, Doctor doctor) {
        LocalDate appointmentDate = date.toLocalDate();
        List<AvailableDate> availableDates = availableDateRepo.findByDoctorId(doctor.getId());

        // istenen tarih doktor ıcın musaıt mı
        return availableDates.stream()
                .anyMatch(availableDate -> availableDate.getAvailableDate().equals(appointmentDate));
    }
    public boolean hasAppointmentAtGivenTime(LocalDateTime date, Doctor doctor) {
        List<Appointment> appointments = this.appointmentRepo.findByDoctorId(doctor.getId());

        return appointments.stream().anyMatch(appointment -> appointment.getAppointmentDate().equals(date));
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDateRangeAndDoctorId(LocalDate startDate, LocalDate endDate, Long doctorId) {
        List<Appointment> filteredAppointments = appointmentRepo.findByAppointmentDateBetweenAndDoctorId(startDate.atStartOfDay(), endDate.atStartOfDay(), doctorId);

        // ModelMapper'ı kullanarak dönüşümü gerçekleştir
        List<AppointmentResponse> appointmentResponses = filteredAppointments.stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());

        return appointmentResponses;
    }
    @Override
    public List<AppointmentResponse> getAppointmentsByDateRangeAndAnimalId(LocalDate startDate, LocalDate endDate, Long animalId) {
        List<Appointment> filteredAppointments = appointmentRepo.findByAppointmentDateBetweenAndAnimalId(startDate.atStartOfDay(), endDate.atStartOfDay(), animalId);

        // ModelMapper'ı kullanarak dönüşümü gerçekleştir
        List<AppointmentResponse> appointmentResponses = filteredAppointments.stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());

        return appointmentResponses;
    }

}
