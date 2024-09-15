package service.impl;

import domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectServiceImpl {
    public Optional<Project> save(Project project);
    public List<Project> getAll();
    public Optional<Project> getByName(String name);
    public Optional<Project> getById(int id);
}
