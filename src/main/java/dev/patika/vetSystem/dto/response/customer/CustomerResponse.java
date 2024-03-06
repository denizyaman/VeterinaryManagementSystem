package dev.patika.vetSystem.dto.response.customer;

import dev.patika.vetSystem.dto.response.animal.AnimalDTO;
import dev.patika.vetSystem.entities.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private Long id;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
    private List<AnimalDTO> animalList;
}
