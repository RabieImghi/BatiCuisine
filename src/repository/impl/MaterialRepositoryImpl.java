package repository.impl;

import domain.Material;
import domain.Project;

import java.util.List;
import java.util.Optional;

public interface MaterialRepositoryImpl {
    Optional<Material> save(Material material);

    List<Material> getAll(Project project);

    void updateVAT(Project project, double vatRate);
}
