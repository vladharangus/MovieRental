package ro.ubb.movie_catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.movie_catalog.core.domain.validators.ValidatorException;
import ro.ubb.movie_catalog.core.service.RentalsService;
import ro.ubb.movie_catalog.web.converter.RentalConverter;
import ro.ubb.movie_catalog.web.dto.RentalDto;
import ro.ubb.movie_catalog.web.dto.RentalsDto;

@RestController
public class RentalController {
    public static final Logger log = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private RentalsService rentalsService;

    @Autowired
    private RentalConverter rentalConverter;

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    RentalsDto getRentals() {
        return new RentalsDto(rentalConverter
                .convertModelsToDtos(rentalsService.getAllRentals()));
    }


    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    RentalDto saveRental(@RequestBody RentalDto rentalDto) throws ValidatorException {
        return rentalConverter.convertModelToDto(rentalsService.addRental(rentalConverter.convertDtoToModel(rentalDto)
        ));
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.PUT)
    RentalDto updateClient(@PathVariable Long id, @RequestBody RentalDto rentalDto) throws ValidatorException {
        return rentalConverter.convertModelToDto(rentalsService.updateRental(id, rentalConverter.convertDtoToModel(rentalDto)));
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteRental(@PathVariable Long id) {
        rentalsService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
