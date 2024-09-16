package domain;

import utils.ProjectStatus;

public class Project {
    private int id;
    private String projectName;
    private double profitMargin;
    private double totalCost;
    private ProjectStatus projectStatus;
    private Client client;

    public Project(String projectName, Client client) {
        this.projectName = projectName;
        this.profitMargin = 0.;
        this.totalCost = 0.;
        this.projectStatus = ProjectStatus.IN_PROGRESS;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }



}
