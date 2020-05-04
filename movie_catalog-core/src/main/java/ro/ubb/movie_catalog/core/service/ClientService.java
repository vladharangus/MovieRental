package ro.ubb.movie_catalog.core.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.movie_catalog.core.domain.entities.Client;
import ro.ubb.movie_catalog.core.domain.validators.ValidatorException;
import ro.ubb.movie_catalog.core.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class ClientService {
    public static final Logger log = LoggerFactory.getLogger(ClientService.class);
    @Autowired
    private ClientRepository repository;


    public Client addClient(Client client) throws ValidatorException {
        log.trace("add client - method entered: client{}", client);
        Client c = repository.save(client);
        log.debug("addClient - added c = {}", c);
        log.trace("add client - method finished");
        return c;
    }

    public List<Client> getAllClients() {
        log.trace("getAllClients - method entered");
        log.trace("getAllClients - method finished");
        return repository.findAll();
    }

    public List<Client> filterClientsByName(String name){
        log.trace("filterClientsByName - method entered");
        Iterable<Client> clients = repository.findAll();
        List<Client> filteredClients = new ArrayList<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(c -> !c.getName().contains(name));
        log.trace("filterClientsByName - method finished");
        return filteredClients;
    }
    public void deleteClient(Long id) {
        log.trace("delete client - method entered");
        repository.deleteById(id);
        log.debug("deleteClient - deleted client");
        log.trace("delete client - method finished");
    }

    public Client getById(Long id) {
        return repository.findById(id).get();
    }

    public Client updateClient(Long id, Client client) throws ValidatorException {
        log.trace("update client - method entered: client{}", client);
        Client newClient = repository.findById(id).orElse(client);
        newClient.setName(client.getName());
        newClient.setAge(client.getAge());
        log.trace("update client - method finished");
        return newClient;
    }
}
