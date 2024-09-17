package service;

import domain.Client;
import domain.Material;
import domain.Project;
import repository.ProjectRepository;
import service.impl.ProjectServiceImpl;
import java.util.Optional;

public class ProjectService  implements ProjectServiceImpl {
    private final ProjectRepository projectRepository = new ProjectRepository();
    public Optional<Project> save(Project project) {
        return projectRepository.save(project);
    }

    public void updateProfitMargin(Project project, double profitMargin) {
        projectRepository.updateProfitMargin(project, profitMargin);
    }
}
