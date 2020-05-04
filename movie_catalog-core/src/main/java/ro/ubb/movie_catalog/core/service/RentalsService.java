package ro.ubb.movie_catalog.core.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.movie_catalog.core.domain.entities.Rentals;
import ro.ubb.movie_catalog.core.domain.validators.ValidatorException;
import ro.ubb.movie_catalog.core.repository.ClientRepository;
import ro.ubb.movie_catalog.core.repository.MovieRepository;
import ro.ubb.movie_catalog.core.repository.RentalRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RentalsService {
    public static final Logger log = LoggerFactory.getLogger(RentalsService.class);

    @Autowired
    private RentalRepository repository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private MovieRepository movieRepository;


    public Rentals addRental(Rentals rental) throws ValidatorException {
        log.trace("add rental - method entered: rental{}", rental);
        Rentals r = new Rentals(0L,0L, 0);
        if (clientRepository.findById(rental.getId()).isPresent() && this.movieRepository.findById(rental.getId()).isPresent())
        {
            r = repository.save(rental);
            log.debug("addRental - added r = {}", r);
        }
        log.trace("add rental - method finished");
        return r;
    }

    public void deleteRental(Long Id){
        log.trace("delete rental - method entered");
        repository.deleteById(Id);
        log.debug("deleteRental - deleted client");
        log.trace("delete rental - method finished");
    }

    public Rentals updateRental(Long id, Rentals rental) throws ValidatorException {
        log.trace("update rental - method entered: rental{}", rental);
        Rentals newRental = repository.findById(id).orElse(rental);
        newRental.setClientID(rental.getClientID());
        newRental.setMovieID(rental.getMovieID());
        newRental.setNumberOfDays(rental.getNumberOfDays());
        log.trace("update rental - method finished");
        return newRental;
    }

    public List<Rentals> getAllRentals(){
        log.trace("get all rentals - method entered");
        Iterable<Rentals> allRentals = this.repository.findAll();
        log.trace("get all rentals - method finished");
        return StreamSupport.stream(allRentals.spliterator(), false).collect(Collectors.toList());
    }

    public Long getMostRentedMovie() {
        log.trace("getMostRentedMovie - method entered");
        Iterable<Rentals> allRentals = this.repository.findAll();
        Map<Long, Long> moviesID = StreamSupport.stream(allRentals.spliterator(), false).map(Rentals::getMovieID)
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        log.trace("getMostRentedMovie - method finished");
        return Collections.max(moviesID.entrySet(), (entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue())).getKey();
    }

    public Long getMostFrequentClient() {
        log.trace("getMostFrequentClient - method entered");
        Iterable<Rentals> allRentals = this.repository.findAll();
        Map<Long, Long> clientsID = StreamSupport.stream(allRentals.spliterator(), false).map(Rentals::getClientID)
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        log.trace("getMostFrequentClient - method finished");
        return Collections.max(clientsID.entrySet(), (entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue())).getKey();

    }
}
