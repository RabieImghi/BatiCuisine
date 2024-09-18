package repository.impl;

import domain.Material;
import domain.Project;

import java.util.List;
import java.util.Optional;

public interface MaterialRepositoryImpl {
    public Optional<Material> save(Material material);

    public List<Material> getAll(Project project);

    public void updateVAT(Project project, double vatRate);
    public Optional<Material> getById(int id);
    public void update(Material material);
}
