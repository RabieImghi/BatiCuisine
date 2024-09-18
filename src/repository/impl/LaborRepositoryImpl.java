package repository.impl;

import domain.Labor;
import domain.Project;

import java.util.List;
import java.util.Optional;

public interface LaborRepositoryImpl {
    Optional<Labor> save(Labor labor);

    List<Labor> getAll(Project project);
}
