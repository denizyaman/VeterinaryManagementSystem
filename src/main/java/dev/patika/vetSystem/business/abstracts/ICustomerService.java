package dev.patika.vetSystem.business.abstracts;

import dev.patika.vetSystem.core.result.Result;
import dev.patika.vetSystem.core.result.ResultData;
import dev.patika.vetSystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.vetSystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.vetSystem.dto.response.CursorResponse;
import dev.patika.vetSystem.dto.response.customer.CustomerResponse;
import dev.patika.vetSystem.entities.Animal;
import dev.patika.vetSystem.entities.Customer;

public interface ICustomerService {

    ResultData<CustomerResponse> save(CustomerSaveRequest customerSaveRequest);

    ResultData<CustomerResponse> get(Long id);
    ResultData<CursorResponse<CustomerResponse>> cursor(int page, int pageSize);
    ResultData<CustomerResponse> update(CustomerUpdateRequest customerUpdateRequest);
    ResultData<CustomerResponse> findByName(String name);
    Result delete (Long id);
}
