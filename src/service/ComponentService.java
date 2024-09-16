package service;

import domain.Component;
import repository.ComponentRepository;
import service.impl.ComponentServiceImpl;

import java.util.Optional;

public class ComponentService implements ComponentServiceImpl {
    private final ComponentRepository componentRepository = new ComponentRepository();
    public Optional<Component> save(Component component) {
        return componentRepository.save(component);
    }
}
