package dev.patika.vetSystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="vaccines")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "vaccine_id" , columnDefinition = "serial")
    private Long id;
    @NotNull
    @Column(name= "vaccine_name")
    private String name;
    @NotNull
    @Column(name= "vaccine_code")
    private String code;
    @NotNull
    @Column(name= "protectionStartDate")
    private LocalDate protectionStartDate;
    @NotNull
    @Column(name= "protectionFinishDate")
    private LocalDate protectionFinishDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "vaccine_animal_id", referencedColumnName = "animal_id")
    @JsonBackReference
    private Animal animal;


}
