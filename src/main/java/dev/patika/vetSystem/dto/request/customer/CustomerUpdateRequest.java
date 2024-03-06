package dev.patika.vetSystem.dto.request.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {
        @Positive(message = "ID Değeri Pozitif Olmak Zorunda")
        private Long id;
        private String name;
        private String phone;
        @NotNull(message = "Emaıl Boş veya Null Olamaz")
        @Email
        private String mail;
        private String address;
        private String city;
}
