package service;

import domain.Client;
import repository.ClientRepository;

import java.util.Optional;

public class ClientService implements ClientServiceImpl {
    private ClientRepository clientRepository = new ClientRepository();
    public Optional<Client> save(Client client) {
        return clientRepository.save(client);
    }

}
