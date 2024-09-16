package service.impl;

import domain.Labor;
import domain.Project;

import java.util.List;
import java.util.Optional;

public interface LaborServiceImpl {
    public Optional<Labor> save(Labor labor);
    public List<Labor> getAll(Project project);
}
