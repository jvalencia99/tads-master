package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.*;

@NotNull
@Data
@AllArgsConstructor
public class Pet {
    @NotNull
    private String codePet;
    @NotNull
    private char genderPet;
    @NotNull
    private int agePet;
    @NotNull
    private String namePet;
    @NotNull
    private LocationPets locationPets;
}