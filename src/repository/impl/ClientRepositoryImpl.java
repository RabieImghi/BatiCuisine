package repository.impl;

import domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepositoryImpl {
    public Optional<Client> save(Client client);
    public Optional<Client> update(Client client);
    public Optional<Client> delete(Client client);
    public Optional<Client> findById(int id);
    public Optional<Client> findByName(String name);
    public List<Client> getAll();
}
