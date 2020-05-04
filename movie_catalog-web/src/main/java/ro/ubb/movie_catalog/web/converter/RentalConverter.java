package ro.ubb.movie_catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.movie_catalog.core.domain.entities.Rentals;
import ro.ubb.movie_catalog.web.dto.RentalDto;

@Component
public class RentalConverter extends BaseConverter<Rentals, RentalDto>{
    @Override
    public Rentals convertDtoToModel(RentalDto dto) {
        Rentals rental = Rentals.builder()
                .clientID(dto.getClientID())
                .movieID(dto.getMovieID())
                .numberOfDays(dto.getNumberOfDays())
                .build();
        rental.setId(dto.getId());
        return rental;
    }

    @Override
    public RentalDto convertModelToDto(Rentals rentals) {
        RentalDto dto = RentalDto.builder()
                .clientID(rentals.getClientID())
                .movieID(rentals.getMovieID())
                .numberOfDays(rentals.getNumberOfDays())
                .build();
        dto.setId(rentals.getId());
        return dto;
    }
}
