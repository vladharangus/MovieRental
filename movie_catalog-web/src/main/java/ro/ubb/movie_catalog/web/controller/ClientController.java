package ro.ubb.movie_catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.movie_catalog.core.domain.validators.ValidatorException;
import ro.ubb.movie_catalog.core.service.ClientService;
import ro.ubb.movie_catalog.web.converter.ClientConverter;
import ro.ubb.movie_catalog.web.dto.ClientDto;
import ro.ubb.movie_catalog.web.dto.ClientsDto;


@RestController
public class ClientController {
    public static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConverter clientConverter;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    ClientsDto getClients() {
        log.trace("getClients - method entered");
        log.trace("getClients - method finished");
        return new ClientsDto(clientConverter
        .convertModelsToDtos(clientService.getAllClients()));
    }

    @RequestMapping(value = "/clients/{name}", method = RequestMethod.GET)
    ClientsDto filterClients(@PathVariable String name) {
        return new ClientsDto(clientConverter
                .convertModelsToDtos(clientService.filterClientsByName(name)));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto saveClient(@RequestBody ClientDto clientDto) throws ValidatorException {
        log.trace("saveClient - method entered");
        log.trace("saveClient - method finished");
        return clientConverter.convertModelToDto(clientService.addClient(clientConverter.convertDtoToModel(clientDto)
        ));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    ClientDto updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) throws ValidatorException {
        log.trace("updateClient - method entered");
        log.trace("updateClient - method finished");
        return clientConverter.convertModelToDto(clientService.updateClient(id, clientConverter.convertDtoToModel(clientDto)));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id) {
        log.trace("deleteClient - method entered");
        clientService.deleteClient(id);
        log.trace("deleteClient - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
    ClientDto getClientbyId(@PathVariable Long id) {
        log.trace("getClientById - method entered");
        log.trace("getClientById - method finsihed");
        return clientConverter.convertModelToDto(clientService.getById(id));
    }
}
