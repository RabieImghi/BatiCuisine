package service.impl;

import domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientServiceImpl {
    public Optional<Client> save(Client client);
    public List<Client> getAll();
    public Optional<Client> getByName(String name);
    public Optional<Client> getById(int id);
    public Optional<Client> update(Client client);
    public Optional<Client> delete(Client client);
}
