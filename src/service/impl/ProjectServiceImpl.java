package service.impl;

import domain.Client;
import domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectServiceImpl {
    public Optional<Project> save(Project project);
    public void updateProfitMargin(Project project, double profitMargin);
    public void updateProfitCost(Project project, double totalCost);
    public List<Project> getAll();
    public Optional<Project> getById(int id);
}
