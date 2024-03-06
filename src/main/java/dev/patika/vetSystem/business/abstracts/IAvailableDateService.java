package dev.patika.vetSystem.business.abstracts;

import dev.patika.vetSystem.core.result.Result;
import dev.patika.vetSystem.core.result.ResultData;
import dev.patika.vetSystem.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.vetSystem.dto.request.availableDate.AvailableDateUpdateRequest;
import dev.patika.vetSystem.dto.response.availableDate.AvailableDateResponse;
import dev.patika.vetSystem.dto.response.CursorResponse;


public interface IAvailableDateService {
    ResultData<AvailableDateResponse> save(AvailableDateSaveRequest availableDateSaveRequest);

    ResultData<AvailableDateResponse> get(Long id);
    ResultData<CursorResponse<AvailableDateResponse>> cursor(int page, int pageSize);
    ResultData<AvailableDateResponse> update(AvailableDateUpdateRequest availableDateUpdateRequest);

    Result delete (Long id);
}
