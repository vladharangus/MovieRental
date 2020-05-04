package ro.ubb.movie_catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.movie_catalog.core.domain.entities.Movie;
import ro.ubb.movie_catalog.core.domain.validators.ValidatorException;
import ro.ubb.movie_catalog.core.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class MovieService {
    public static final Logger log = LoggerFactory.getLogger(MovieService.class);
    @Autowired
    private MovieRepository repository;

    public Movie addMovie(Movie movie) throws ValidatorException {
        log.trace("add movie - method entered: movie{}", movie);
        Movie m = repository.save(movie);
        log.debug("addMovie - added m = {}", m);
        log.trace("add movie - method finished");
        return m;
    }

    public List<Movie> getAllMovies(){
        log.trace("getAllMovies - method entered");
        Iterable<Movie> allMovies = this.repository.findAll();
        log.trace("getAllMovies - method finished");
        return StreamSupport.stream(allMovies.spliterator(), false).collect(Collectors.toList());
    }

    public List<Movie> filterMoviesByDirector(String director){
        log.trace("filterMoviesByDirector - method entered");
        Iterable<Movie> movies = this.repository.findAll();
        List<Movie> filteredMovies = new ArrayList<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie-> !movie.getDirector().equals(director));
        log.trace("filterMoviesByDirector - method finsished");
        return filteredMovies;
    }

    public Movie getById(Long id) {
        Movie m = repository.findById(id).get();
        return m;
    }

    public void deleteMovie(Long id){
        log.trace("delete movie - method entered");
        repository.deleteById(id);
        log.debug("deleteMovie - deleted client");
        log.trace("delete movie - method finished");
    }

    public Movie updateMovie(Long id, Movie movie) throws ValidatorException {
        log.trace("update movie - method entered: movie{}", movie);
        Movie newMovie = repository.findById(id).orElse(movie);
        newMovie.setName(movie.getName());
        newMovie.setDirector(movie.getDirector());
        log.trace("update movie - method finished");
        return newMovie;
    }

}
