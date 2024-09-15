package service;

import domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientServiceImpl {
    public Optional<Client> save(Client client);
    public List<Client> getAll();
    public Optional<Client> getByName(String name);
}
