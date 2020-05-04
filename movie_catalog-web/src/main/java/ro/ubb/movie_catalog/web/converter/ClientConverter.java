package ro.ubb.movie_catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.movie_catalog.web.dto.ClientDto;
import ro.ubb.movie_catalog.core.domain.entities.Client;
@Component
public class ClientConverter extends BaseConverter<Client, ClientDto>{
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        return new Client(dto.getName(), dto.getAge());
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto dto = new ClientDto(client.getName(), client.getAge());

        dto.setId(client.getId());
        return dto;
    }
}
