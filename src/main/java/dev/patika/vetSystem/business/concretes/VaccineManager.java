package dev.patika.vetSystem.business.concretes;

import dev.patika.vetSystem.business.abstracts.IVaccineService;
import dev.patika.vetSystem.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetSystem.core.exception.NotFoundException;
import dev.patika.vetSystem.core.result.Result;
import dev.patika.vetSystem.core.result.ResultData;
import dev.patika.vetSystem.core.utilites.Msg;
import dev.patika.vetSystem.core.utilites.ResultHelper;
import dev.patika.vetSystem.dao.AnimalRepo;
import dev.patika.vetSystem.dao.VaccineRepo;
import dev.patika.vetSystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.vetSystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.vetSystem.dto.response.CursorResponse;
import dev.patika.vetSystem.dto.response.vaccine.VaccineResponse;
import dev.patika.vetSystem.entities.Animal;
import dev.patika.vetSystem.entities.Doctor;
import dev.patika.vetSystem.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class VaccineManager implements IVaccineService {
    private final IModelMapperService modelMapper;
    private final VaccineRepo vaccineRepo;
    private final AnimalRepo animalRepo;

    public VaccineManager(IModelMapperService modelMapper, VaccineRepo vaccineRepo,AnimalRepo animalRepo) {
        this.modelMapper = modelMapper;
        this.vaccineRepo = vaccineRepo;
        this.animalRepo = animalRepo;
    }

    @Override
    public ResultData<VaccineResponse> save(VaccineSaveRequest vaccineSaveRequest) {
        Animal animal=animalRepo.findById(vaccineSaveRequest.getAnimal_id()).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
        Vaccine saveVaccine= this.modelMapper.forRequest().map(vaccineSaveRequest, Vaccine.class);
        LocalDate today = LocalDate.now();

        // Aynı ad, kod ve hayvan ID'si ile başka bir aşı kaydı var mı kontrol et
        Optional<Vaccine> existingVaccine = vaccineRepo.findByAnimalIdAndName(vaccineSaveRequest.getAnimal_id(), vaccineSaveRequest.getName());

        // Eğer aşı koruyuculuk tarihi bitmemişse ve var olan bir aşı kaydı varsa hata fırlat
        if (!existingVaccine.isEmpty() && existingVaccine.get().getProtectionFinishDate().isAfter(today)) {
            throw new IllegalArgumentException("Aynı ad, kod ve hayvan ID'sine sahip bir aşı kaydı zaten mevcut ve koruyuculuk süresi devam ediyor!");
        }
        saveVaccine.setAnimal(animal);
        this.vaccineRepo.save(saveVaccine);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveVaccine, VaccineResponse.class));
    }

    @Override
    public ResultData<VaccineResponse> get(Long id) {
        Vaccine vaccine=vaccineRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
        return ResultHelper.success(this.modelMapper.forResponse().map(vaccine,VaccineResponse.class));
    }

    @Override
    public ResultData<CursorResponse<VaccineResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Vaccine> vaccinePage=vaccineRepo.findAll(pageable);

        Page<VaccineResponse> vaccineResponsePage = vaccinePage
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));
        return ResultHelper.cursor(vaccineResponsePage);    }

    @Override
    public ResultData<VaccineResponse> update(VaccineUpdateRequest vaccineUpdateRequest) {
        Animal animal=animalRepo.findById(vaccineUpdateRequest.getAnimal_id()).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
        Vaccine updateVaccine = this.modelMapper.forRequest().map(vaccineUpdateRequest,Vaccine.class);
        this.get(vaccineUpdateRequest.getId());
        updateVaccine.setAnimal(animal);
        this.vaccineRepo.save(updateVaccine);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateVaccine,VaccineResponse.class));
    }

    @Override
    public Result delete(Long id) {
        ResultData<VaccineResponse> vaccine = this.get(id);
        Vaccine vaccine1=this.modelMapper.forResponse().map(vaccine.getData(), Vaccine.class);
        this.vaccineRepo.delete(vaccine1);
        return ResultHelper.ok();    }
}
