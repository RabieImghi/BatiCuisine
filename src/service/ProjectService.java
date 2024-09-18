package service;

import domain.Client;
import domain.Material;
import domain.Project;
import repository.ProjectRepository;
import service.impl.ProjectServiceImpl;

import java.util.List;
import java.util.Optional;

public class ProjectService  implements ProjectServiceImpl {
    private final ProjectRepository projectRepository = new ProjectRepository();
    public Optional<Project> save(Project project) {
        return projectRepository.save(project);
    }

    public void updateProfitMargin(Project project, double profitMargin) {
        projectRepository.updateProfitMargin(project, profitMargin);
    }
    public void updateProfitCost(Project project, double totalCost){
        projectRepository.updateProfitCost(project,totalCost);
    }
    public List<Project> getAll(){
        return projectRepository.getAll();
    }
    public Optional<Project> getById(int id){
        return projectRepository.getById(id);
    }
}
