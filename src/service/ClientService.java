package service;

import domain.Client;
import repository.ClientRepository;

import java.util.List;
import java.util.Optional;

public class ClientService implements ClientServiceImpl {
    private ClientRepository clientRepository = new ClientRepository();
    public Optional<Client> save(Client client) {
        return clientRepository.save(client);
    }
    public List<Client> getAll(){
        return clientRepository.getAll();
    }
    public Optional<Client> getByName(String name){
        return  clientRepository.findByName(name);
    }

}
