package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.RangeAgeKids;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;

    @Autowired
    private RangeAgeService rangeAgeService;

    //Obtener niños
    @GetMapping
    public ResponseEntity<ResponseDTO> getKids() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listSEService.getKids(), null), HttpStatus.OK);
    }

    //Adicionar niños
    @GetMapping(path = "/add")
    public ResponseEntity<ResponseDTO> add(@RequestBody Kid kid) {
        try {
            if (kid == null) {
                throw new ListSEException("El niño no tiene datos");
            }
            listSEService.getKids().add(kid);
            return new ResponseEntity<>(new ResponseDTO(200,
                    "El niño ha sido añadido", null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error al añadir el niño", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Invertir lista
    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert() {
        try {
            listSEService.invert();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha invertido la lista",
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al invertir la lista",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Niños al inicio y niñas al final
    @GetMapping("/orderboystostart")
    public ResponseEntity<ResponseDTO> orderBoysToStart() {
        try {
            listSEService.getKids().orderBoysToStart();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se han añadido los niños al inicio, las niñas al final.",
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al ordenar los niños.", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Intercalar niño, niña, niño, niña
    @GetMapping(path="/boyintercalategirl")
    public ResponseEntity<ResponseDTO> boyIntercalateGirl()  {
        try {
            listSEService.getKids().intercalateBoysGirls();
            return new ResponseEntity<>(new ResponseDTO(200, "Los niños se han intercalado.",
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al intercalar los niños", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Dada una edad eliminar a los niños de la edad dada
    @GetMapping(path = "/delete/{age}")
    public ResponseEntity<ResponseDTO> deleteKidByAge(@PathVariable byte age)  {
        try {
            listSEService.deleteByAge(age);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Los niños con la edad " + age + "han sido eliminados.",
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al eliminar los niños.",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener el promedio de edad de los niños de la lista
    @GetMapping(path="/averageage")
    public ResponseEntity<ResponseDTO> averageAge() {
        try {
            listSEService.getKids().averageAge();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha calculado el promedio de edad",
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al calcular la edad promedio.",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Generar un reporte que me diga cuantos niños hay de cada ciudad.
    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation() {
        try {
            List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
            for (Location loc : locationService.getLocations()) {
                int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
                if (count > 0) {
                    kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, kidsByLocationDTOList,
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al obtener los niños por ubicación.", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/kidsbydepartments")
    public ResponseEntity<ResponseDTO> getKidsByDepartmentCode() {
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        try {
            for (Location loc : locationService.getLocations()) {
                int count = listSEService.getKids().getCountKidsByDepartmentCode(loc.getCode());
                if (count > 0) {
                    kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, kidsByLocationDTOList,
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error al obtener la lista de niños por departamento.",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKidsLocationGenders(@PathVariable byte age) {
        ReportKidsDTO report =
                new ReportKidsDTO(locationService.getLocationsByCodeSize(8));
        listSEService.getKids().getReportKidsByLocationGendersByAge(age,report);
        return new ResponseEntity<>(new ResponseDTO(
                200, report,null),HttpStatus.OK);
    }

    //Método que me permita decirle a un niño determinado que adelante un numero de posiciones dadas
    @GetMapping(path="/passpositions/{positions}")
    public ResponseEntity<ResponseDTO> passByPosition(@PathVariable String identification, int positions) {
        try {
            listSEService.getKids().passByPosition(identification, positions);
            return new ResponseEntity<>(new ResponseDTO(200, "El niño ha adelantado de posición", null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ha ocurrido un error al adelantar la posición del niño", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Método que me permita decirle a un niño determinado que pierda un numero de posiciones dadas
    @GetMapping(path="/afterwardspositions")
    public ResponseEntity<ResponseDTO> afterwardsPositions(@PathVariable String identification, int positions) throws ListSEException {
        try {
            listSEService.getKids().afterwardsPositions(identification, positions);
            return new ResponseEntity<>(new ResponseDTO(200, "El niño ha sido movido de posición", null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error al intentar mover al niño de posición", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody Kid kid) throws ListSEException {
        listSEService.addToStart(kid);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha añadido al inicio.",
                null), HttpStatus.OK);
    }

    @GetMapping("/addtofinal")
    public ResponseEntity<ResponseDTO> addToFinal(@RequestBody Kid kid) throws ListSEException {
        listSEService.addToStart(kid);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha añadido al final",
                null), HttpStatus.OK);
    }

    //Implementar un método que me permita enviar al final de la lista a los niños que su nombre inicie con una letra dada
    @GetMapping(path="/kidletter/{initial}")
    public ResponseEntity<ResponseDTO> boysByLetter(@PathVariable char initial) throws ListSEException {
        listSEService.getKids().kidsByLetter(Character.toUpperCase(initial));
        return new ResponseEntity<>(new ResponseDTO(200, "Los niños con esa letra se han enviado al final de la lista.",
                null), HttpStatus.OK);
    }

    //Obtener un informe de niños por rango de edades
    @GetMapping(path="/rangeagekids")
    public ResponseEntity<ResponseDTO> getRangeAgeKids() throws ListSEException {
        List<RangeAgesKidsDTO> kidsRangeDTOList = new ArrayList<>();

        for(RangeAgeKids i: rangeAgeService.getRanges()){
            int quantity = listSEService.getKids().rangeByAge(i.getFrom(), i.getTo());
            kidsRangeDTOList.add(new RangeAgesKidsDTO(i, quantity));
        }
        return new ResponseEntity<>(new ResponseDTO(200, kidsRangeDTOList, null), HttpStatus.OK);
    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() throws ListSEException {
        listSEService.getKids().changesExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha intercambiado los extremos ", null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO) {
        Location location = locationService.getLocationsByCode(kidDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe ", null), HttpStatus.OK);
        } try {
            listSEService.getKids().add(new Kid(kidDTO.getIdentification(),
                    kidDTO.getName(), kidDTO.getAge(),
                    kidDTO.getGender(), location));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, "Ya existe un niño con ese código", null
            ), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha añadido", null), HttpStatus.OK);
    }


}