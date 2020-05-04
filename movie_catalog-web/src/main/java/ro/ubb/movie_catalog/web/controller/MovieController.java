package ro.ubb.movie_catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.movie_catalog.core.domain.validators.ValidatorException;
import ro.ubb.movie_catalog.core.service.MovieService;
import ro.ubb.movie_catalog.web.converter.MovieConverter;
import ro.ubb.movie_catalog.web.dto.MovieDto;
import ro.ubb.movie_catalog.web.dto.MoviesDto;

@RestController
public class MovieController {
    public static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter movieConverter;

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    MoviesDto getMovies() {
        return new MoviesDto(movieConverter
                .convertModelsToDtos(movieService.getAllMovies()));
    }

    @RequestMapping(value = "/movies/{director}", method = RequestMethod.GET)
    MoviesDto filterMovies(@PathVariable String director) {
        return new MoviesDto(movieConverter
                .convertModelsToDtos(movieService.filterMoviesByDirector(director)));
    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    MovieDto saveMovie(@RequestBody MovieDto movieDto) throws ValidatorException {
        return movieConverter.convertModelToDto(movieService.addMovie(movieConverter.convertDtoToModel(movieDto)
        ));
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.PUT)
    MovieDto updateClient(@PathVariable Long id, @RequestBody MovieDto movieDto) throws ValidatorException {
        return movieConverter.convertModelToDto(movieService.updateMovie(id, movieConverter.convertDtoToModel(movieDto)));
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.GET)
    MovieDto getMoviebyId(@PathVariable Long id) {
        return movieConverter.convertModelToDto(movieService.getById(id));
    }
}
