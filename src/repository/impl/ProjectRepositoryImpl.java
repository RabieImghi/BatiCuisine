package repository.impl;

import domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryImpl {
    Optional<Project> save(Project project);

    void updateProfitMargin(Project project, double profitMargin);

    void updateProfitCost(Project project, double totalCost);

    List<Project> getAll();

    Optional<Project> getById(int id);

    void delete(Project project);

    void update(Project project);
}
