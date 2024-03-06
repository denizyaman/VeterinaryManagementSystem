package dev.patika.vetSystem.business.concretes;

import dev.patika.vetSystem.business.abstracts.IAvailableDateService;
import dev.patika.vetSystem.business.abstracts.IDoctorService;
import dev.patika.vetSystem.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetSystem.core.exception.NotFoundException;
import dev.patika.vetSystem.core.result.Result;
import dev.patika.vetSystem.core.result.ResultData;
import dev.patika.vetSystem.core.utilites.Msg;
import dev.patika.vetSystem.core.utilites.ResultHelper;
import dev.patika.vetSystem.dao.AvailableDateRepo;
import dev.patika.vetSystem.dao.DoctorRepo;
import dev.patika.vetSystem.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.vetSystem.dto.request.availableDate.AvailableDateUpdateRequest;
import dev.patika.vetSystem.dto.response.availableDate.AvailableDateResponse;
import dev.patika.vetSystem.dto.response.CursorResponse;
import dev.patika.vetSystem.entities.AvailableDate;
import dev.patika.vetSystem.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AvailableDateManager implements IAvailableDateService {
    private final IModelMapperService modelMapper;
    private final AvailableDateRepo availableDateRepo;
    private final DoctorRepo doctorRepo;

    public AvailableDateManager(IModelMapperService modelMapper, AvailableDateRepo availableDateRepo,DoctorRepo doctorRepo) {
        this.modelMapper = modelMapper;
        this.availableDateRepo = availableDateRepo;
        this.doctorRepo = doctorRepo;
    }


    @Override
    public ResultData<AvailableDateResponse> save(AvailableDateSaveRequest availableDateSaveRequest) {
        Doctor doctor=doctorRepo.findById(availableDateSaveRequest.getDoctor_id()).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
        AvailableDate saveAvailableDate= this.modelMapper.forRequest().map(availableDateSaveRequest, AvailableDate.class);
        saveAvailableDate.setDoctor(doctor);
        this.availableDateRepo.save(saveAvailableDate);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAvailableDate, AvailableDateResponse.class));
    }

    @Override
    public ResultData<AvailableDateResponse> get(Long id) {
        AvailableDate availableDate=availableDateRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
        return ResultHelper.success(this.modelMapper.forResponse().map(availableDate,AvailableDateResponse.class));
    }

    @Override
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<AvailableDate> availableDatePage=availableDateRepo.findAll(pageable);

        Page<AvailableDateResponse> availableDateResponsePage = availableDatePage
                .map(availableDate -> this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class));
        return ResultHelper.cursor(availableDateResponsePage);
    }

    @Override
    public ResultData<AvailableDateResponse> update(AvailableDateUpdateRequest availableDateUpdateRequest) {
        Doctor doctor=doctorRepo.findById(availableDateUpdateRequest.getDoctor_id()).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
        AvailableDate updateAvailableDate = this.modelMapper.forRequest().map(availableDateUpdateRequest,AvailableDate.class);
        this.get(availableDateUpdateRequest.getId());
        updateAvailableDate.setDoctor(doctor);
        this.availableDateRepo.save(updateAvailableDate);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAvailableDate,AvailableDateResponse.class));
    }

    @Override
    public Result delete(Long id) {
        ResultData<AvailableDateResponse> availableDate = this.get(id);
        AvailableDate availableDate1=this.modelMapper.forResponse().map(availableDate.getData(), AvailableDate.class);
        this.availableDateRepo.delete(availableDate1);
        return ResultHelper.ok();
    }
}

