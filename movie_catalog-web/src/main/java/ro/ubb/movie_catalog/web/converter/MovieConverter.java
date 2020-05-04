package ro.ubb.movie_catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.movie_catalog.core.domain.entities.Movie;
import ro.ubb.movie_catalog.web.dto.MovieDto;

@Component
public class MovieConverter extends BaseConverter<Movie, MovieDto>{
    @Override
    public Movie convertDtoToModel(MovieDto dto) {
        Movie movie = Movie.builder()
                .name(dto.getName())
                .director(dto.getDirector())
                .build();
        movie.setId(dto.getId());
        return movie;
    }

    @Override
    public MovieDto convertModelToDto(Movie movie) {
        MovieDto dto = MovieDto.builder()
                .name(movie.getName())
                .director(movie.getDirector())
                .build();
        dto.setId(movie.getId());
        return dto;
    }
}
