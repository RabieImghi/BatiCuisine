package service.impl;

import domain.Component;

import java.util.Optional;

public interface ComponentServiceImpl {
    public Optional<Component> save(Component component);
}
