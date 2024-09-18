package service.impl;

import domain.Labor;
import domain.Material;
import domain.Project;

import java.util.List;
import java.util.Optional;

public interface LaborServiceImpl {
    public Optional<Labor> save(Labor labor);
    public List<Labor> getAll(Project project);
    public double totalCostLabor(List<Labor> listMaterial);
    public Optional<Labor> getById(int id);
    public void delete(Labor labor);
    public void update(Labor labor);
}
