package dev.patika.vetSystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "animal_id" , columnDefinition = "serial")
    private Long id;

    @Column(name= "animal_name")
    private String name;

    @Column(name= "species")
    private String species;

    @Column(name= "breed")
    private String breed;

    @Column(name= "gender")
    private String gender;

    @Column(name= "colour")
    private String colour;

    @Column(name= "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "animal_customer_id", referencedColumnName = "customer_id")
    @JsonManagedReference
    private Customer customer;

    @OneToMany(mappedBy = "animal",cascade = CascadeType.MERGE)
    @JsonManagedReference
    private List<Vaccine> vaccineList;


    @OneToMany(mappedBy = "animal",cascade = CascadeType.MERGE)
    @JsonManagedReference
    private List<Appointment> appointmentList;
}
