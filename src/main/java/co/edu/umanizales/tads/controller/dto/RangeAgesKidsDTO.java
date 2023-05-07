package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.RangeAgeKids;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RangeAgesKidsDTO {
    private RangeAgeKids range;
    int quantity;
}