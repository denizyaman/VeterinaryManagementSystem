package dev.patika.vetSystem.business.concretes;

import dev.patika.vetSystem.business.abstracts.IAnimalService;
import dev.patika.vetSystem.core.config.ModelMapper.IModelMapperService;
import dev.patika.vetSystem.core.exception.NotFoundException;
import dev.patika.vetSystem.core.result.Result;
import dev.patika.vetSystem.core.result.ResultData;
import dev.patika.vetSystem.core.utilites.Msg;
import dev.patika.vetSystem.core.utilites.ResultHelper;
import dev.patika.vetSystem.dao.AnimalRepo;
import dev.patika.vetSystem.dao.CustomerRepo;
import dev.patika.vetSystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.vetSystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.vetSystem.dto.response.CursorResponse;
import dev.patika.vetSystem.dto.response.animal.AnimalResponse;

import dev.patika.vetSystem.entities.Animal;

import dev.patika.vetSystem.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnimalManager implements IAnimalService {
    private final AnimalRepo animalRepo;
    private final CustomerRepo customerRepo;
    private final IModelMapperService modelMapper;

    public AnimalManager(CustomerRepo customerRepo,AnimalRepo animalRepo, IModelMapperService modelMapper) {
        this.animalRepo = animalRepo;
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResultData<AnimalResponse> save(AnimalSaveRequest animalSaveRequest) {
        Customer customer=customerRepo.findById(animalSaveRequest.getCustomerId()).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
        Animal saveAnimal= this.modelMapper.forRequest().map(animalSaveRequest, Animal.class);
        saveAnimal.setCustomer(customer);
        this.animalRepo.save(saveAnimal);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAnimal, AnimalResponse.class));
    }

    @Override
    public ResultData<AnimalResponse> get(Long id) {
        Animal animal=animalRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
        return ResultHelper.success(this.modelMapper.forResponse().map(animal,AnimalResponse.class));

    }

    @Override
    public ResultData<CursorResponse<AnimalResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Animal> animalPage=animalRepo.findAll(pageable);
        Page<AnimalResponse> animalResponsePage = animalPage
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class));
        return ResultHelper.cursor(animalResponsePage);
    }

    @Override
    public ResultData<AnimalResponse> update(AnimalUpdateRequest animalUpdateRequest) {
        Customer customer=customerRepo.findById(animalUpdateRequest.getCustomerId()).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
        Animal updateAnimal = this.modelMapper.forRequest().map(animalUpdateRequest,Animal.class);
        this.get(animalUpdateRequest.getId());
        updateAnimal.setCustomer(customer);
        this.animalRepo.save(updateAnimal);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAnimal,AnimalResponse.class));
    }

    @Override
    public Result delete(Long id) {
        ResultData<AnimalResponse> animal = this.get(id);
        Animal animal1=this.modelMapper.forResponse().map(animal.getData(), Animal.class);
        this.animalRepo.delete(animal1);
        return ResultHelper.ok();
    }

    @Override
    public ResultData<AnimalResponse> findByName(String name) {
        Animal animal=this.animalRepo.findByName(name).orElseThrow(()->new NotFoundException(Msg.NOT_FOUND));
        //Animal animalResponse =this.modelMapper.forResponse().map(animal, Animal.class);
        return ResultHelper.success(this.modelMapper.forResponse().map(animal,AnimalResponse.class));
    }
}
