package service.impl;

import domain.Material;
import domain.Project;

import java.util.List;
import java.util.Optional;

public interface MaterialServiceImpl {
    public Optional<Material> save(Material material);

    public List<Material> getAll(Project project);
    public void updateVAT(Project project, double vatRate);
    public double totalCostMaterial(List<Material> listMaterial);
    public Optional<Material> getById(int id);
    public void update(Material material);
    public void delete(Material material);
}
