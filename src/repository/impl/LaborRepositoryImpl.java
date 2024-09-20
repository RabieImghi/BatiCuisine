package repository.impl;

import domain.Labor;
import domain.Project;

import java.util.List;
import java.util.Optional;

public interface LaborRepositoryImpl {
    public Optional<Labor> save(Labor labor);

    public List<Labor> getAll(Project project);
    public Optional<Labor> getById(int id);
    public void delete(Labor labor);
    public void update(Labor labor);
}
