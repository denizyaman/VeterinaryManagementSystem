package dev.patika.vetSystem.business.concretes;

import dev.patika.vetSystem.business.abstracts.IDoctorService;
import dev.patika.vetSystem.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetSystem.core.exception.NotFoundException;
import dev.patika.vetSystem.core.result.Result;
import dev.patika.vetSystem.core.result.ResultData;
import dev.patika.vetSystem.core.utilites.Msg;
import dev.patika.vetSystem.core.utilites.ResultHelper;
import dev.patika.vetSystem.dao.DoctorRepo;
import dev.patika.vetSystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.vetSystem.dto.request.customer.DoctorSaveRequest;
import dev.patika.vetSystem.dto.response.CursorResponse;
import dev.patika.vetSystem.dto.response.doctor.DoctorResponse;
import dev.patika.vetSystem.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorManager implements IDoctorService {
    private final IModelMapperService modelMapper;
    private final DoctorRepo doctorRepo;

    public DoctorManager(DoctorRepo doctorRepo, IModelMapperService modelMapper) {
        this.modelMapper = modelMapper;
        this.doctorRepo=doctorRepo;
    }

    @Override
    public ResultData<DoctorResponse> save(DoctorSaveRequest doctorSaveRequest) {
        Doctor saveDoctor= this.modelMapper.forRequest().map(doctorSaveRequest, Doctor.class);
        this.doctorRepo.save(saveDoctor);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveDoctor, DoctorResponse.class));
    }

    @Override
    public ResultData<DoctorResponse> get(Long id) {
        Doctor doctor=doctorRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
        return ResultHelper.success(this.modelMapper.forResponse().map(doctor,DoctorResponse.class));
    }

    @Override
    public ResultData<CursorResponse<DoctorResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Doctor>doctorPage=doctorRepo.findAll(pageable);

        Page<DoctorResponse> doctorResponsePage = doctorPage
                .map(doctor -> this.modelMapper.forResponse().map(doctor, DoctorResponse.class));
        return ResultHelper.cursor(doctorResponsePage);
    }

    @Override
    public ResultData<DoctorResponse> update(DoctorUpdateRequest doctorUpdateRequest) {
        Doctor updateDoctor = this.modelMapper.forRequest().map(doctorUpdateRequest,Doctor.class);
        this.get(doctorUpdateRequest.getId());
        this.doctorRepo.save(updateDoctor);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateDoctor,DoctorResponse.class));
    }

    @Override
    public Result delete(Long id) {
        ResultData<DoctorResponse> doctor = this.get(id);
        Doctor doctor1=this.modelMapper.forResponse().map(doctor.getData(), Doctor.class);
        this.doctorRepo.delete(doctor1);
        return ResultHelper.ok();
    }
}
