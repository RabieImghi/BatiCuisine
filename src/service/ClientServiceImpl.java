package service;

import domain.Client;

import java.util.Optional;

public interface ClientServiceImpl {
    public Optional<Client> save(Client client);
}
