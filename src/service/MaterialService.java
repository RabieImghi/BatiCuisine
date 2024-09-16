package service;

import domain.Material;
import domain.Project;
import repository.MaterialRepository;
import service.impl.MaterialServiceImpl;

import java.util.List;
import java.util.Optional;

public class MaterialService implements MaterialServiceImpl {
    private final MaterialRepository materialRepository = new MaterialRepository();
    public Optional<Material> save(Material material) {
        return materialRepository.save(material);
    }

    public List<Material> getAll(Project project) {
        return materialRepository.getAll(project);
    }
}
