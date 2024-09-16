package service.impl;

import domain.Client;
import domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectServiceImpl {
    public Optional<Project> save(Project project);
}
