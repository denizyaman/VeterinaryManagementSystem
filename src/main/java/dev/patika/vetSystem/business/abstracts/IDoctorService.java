package dev.patika.vetSystem.business.abstracts;
import dev.patika.vetSystem.core.result.Result;
import dev.patika.vetSystem.core.result.ResultData;
import dev.patika.vetSystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.vetSystem.dto.request.customer.DoctorSaveRequest;
import dev.patika.vetSystem.dto.response.CursorResponse;

import dev.patika.vetSystem.dto.response.doctor.DoctorResponse;

public interface IDoctorService {
    ResultData<DoctorResponse> save(DoctorSaveRequest doctorSaveRequest);

    ResultData<DoctorResponse> get(Long id);
    ResultData<CursorResponse<DoctorResponse>> cursor(int page, int pageSize);
    ResultData<DoctorResponse> update(DoctorUpdateRequest doctorUpdateRequest);

    Result delete (Long id);

}
