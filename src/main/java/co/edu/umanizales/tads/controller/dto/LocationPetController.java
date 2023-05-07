package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.service.LocationPetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/locationpets")
public class LocationPetController {
    @Autowired
    private LocationPetsService locationPetsService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllLocations(){
        return new ResponseEntity<>(new ResponseDTO(
                200,locationPetsService.getLocationsPets(),null), HttpStatus.OK);
    }

    @GetMapping(path = "/countriespets")
    public ResponseEntity<ResponseDTO> getCountries(){
        return new ResponseEntity<>(new ResponseDTO(
                200,locationPetsService.getLocationsPetsByCodeSize(3),null), HttpStatus.OK);
    }

    @GetMapping(path = "/departamentspets")
    public ResponseEntity<ResponseDTO> getDepartments(){
        return new ResponseEntity<>(new ResponseDTO(
                200,locationPetsService.getLocationsPetsByCodeSize(5),null), HttpStatus.OK);
    }
}