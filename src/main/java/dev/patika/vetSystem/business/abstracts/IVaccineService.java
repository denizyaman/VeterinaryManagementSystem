package dev.patika.vetSystem.business.abstracts;

import dev.patika.vetSystem.core.result.Result;
import dev.patika.vetSystem.core.result.ResultData;
import dev.patika.vetSystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.vetSystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.vetSystem.dto.response.CursorResponse;
import dev.patika.vetSystem.dto.response.vaccine.VaccineResponse;
import org.springframework.stereotype.Service;


public interface IVaccineService {
    ResultData<VaccineResponse> save(VaccineSaveRequest vaccineSaveRequest);

    ResultData<VaccineResponse> get(Long id);
    ResultData<CursorResponse<VaccineResponse>> cursor(int page, int pageSize);
    ResultData<VaccineResponse> update(VaccineUpdateRequest vaccineUpdateRequest);

    Result delete (Long id);
}
