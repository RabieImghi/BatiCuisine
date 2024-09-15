package service;

import domain.Client;
import domain.Project;
import repository.ProjectRepository;
import service.impl.ProjectServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectService  implements ProjectServiceImpl {
    private final ProjectRepository projectRepository = new ProjectRepository();
    public Optional<Project> save(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAll() {
        return new ArrayList<>();
    }

    public Optional<Project> getByName(String name) {
        return Optional.empty();
    }

    public Optional<Project> getById(int id) {
        return Optional.empty();
    }
}
