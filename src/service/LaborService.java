package service;

import domain.Labor;
import domain.Project;
import repository.LaborRepository;
import service.impl.LaborServiceImpl;

import java.util.List;
import java.util.Optional;

public class LaborService implements LaborServiceImpl {
    private final LaborRepository laborRepository = new LaborRepository();
    public Optional<Labor> save(Labor labor) {
        return laborRepository.save(labor);
    }

    public List<Labor> getAll(Project project) {
        return laborRepository.getAll(project);
    }
    public double totalCostLabor(List<Labor> listMaterial){
        return listMaterial.stream().mapToDouble(labor -> labor.getHourlyRate() * labor.getHoursWorked() * labor.getWorkerProductivity() ).sum();
    }
}
