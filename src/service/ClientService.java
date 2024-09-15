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
    public Optional<Client> getById(int id){
        return clientRepository.findById(id);
    }
    public Optional<Client> update(Client client){
        return clientRepository.update(client);
    }
    public Optional<Client> delete(Client client){
        return clientRepository.delete(client);
    }

}
