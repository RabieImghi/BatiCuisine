package service;

import domain.Project;
import service.impl.ProjectServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectService  implements ProjectServiceImpl {
    public Optional<Project> save(Project project) {
        return Optional.ofNullable(project);
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
