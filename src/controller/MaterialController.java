package controller;

import domain.Material;
import domain.Project;
import service.MaterialService;
import utils.ComponentType;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MaterialController {
    private final MaterialService materialService = new MaterialService();
    private final Scanner scanner = new Scanner(System.in);
    public Optional<Material> save(Project project){
        System.out.print("Do you want to add a material? (yes/no)");
        String option = scanner.nextLine();
        if(option.equals("yes")){
            System.out.print("Enter the material name : ");
            String name = scanner.nextLine();
            System.out.print("Enter the quantity of this material (in m²): ");
            double quantity = scanner.nextDouble();
            System.out.print("Enter the unit cost of this material (€/m²): ");
            double unitCost = scanner.nextDouble();
            System.out.print("Enter the cost of transporting this material (€): ");
            double transportCost = scanner.nextDouble();
            System.out.print("Enter the material quality coefficient (1.0 = standard, > 1.0 = high quality):");
            double coefficientQuality = scanner.nextDouble();
            String def = scanner.nextLine();
            Material material = new Material(name,String.valueOf(ComponentType.MATERIAL),0,unitCost,quantity,transportCost,coefficientQuality,project);
            materialService.save(material).ifPresentOrElse(material1 -> {
                System.out.println("Material added successfully");
            },()-> {
                System.out.println("Material not added");
            });
            return Optional.of(material);
        }else {
            System.out.println("Material Add Cancelled");
            return Optional.empty();
        }

    }
    public void updateVAT(Project project){
        System.out.print("Enter the VAT rate : ");
        double vatRate = scanner.nextDouble();
        materialService.updateVAT(project,vatRate);
    }

    public List<Material> getAll(Project project){
        return materialService.getAll(project);
    }
    public double totalCostMaterial(List<Material> listMaterial){
        return materialService.totalCostMaterial(listMaterial);
    }
    public void manageMaterial(){
        boolean exit = false;
        do{
            System.out.println("1 -> Add a material");
            System.out.println("2 -> Update a material");
            System.out.println("3 -> Delete a material");
            System.out.println("4 -> Exit");
            String option = scanner.nextLine();
            switch (option){
                case "1":
                    saveNewMaterial();
                    break;
                case "2":
                    updateMaterial();
                    break;
                case "3":
                    deleteMaterial();
                    break;
                case "4":
                    exit = true;
                    break;
                default:
                    System.out.println("Incorrect choice");
            }
        }while (!exit);
    }
    public void updateMaterial(){
        Optional<Project> project ;
        int id=0;
        do {
            ProjectController projectController = new ProjectController();
            projectController.getAll();
            System.out.println("Select the project for which you want to update a material (Id) : ");
            id = scanner.nextInt();
            scanner.nextLine();
            project = projectController.getById(id);
            project.ifPresentOrElse(project1 -> {
                List<Material> listMaterial = getAll(project1);
                listMaterial.forEach(System.out::println);
                System.out.println("Select the material to update (Id) : ");
                int idMaterial = scanner.nextInt();
                scanner.nextLine();
                Optional<Material> material = materialService.getById(idMaterial);
                material.ifPresentOrElse(material1 -> {
                    System.out.println("Do you want to update the name of the material? (yes/no)");
                    String option = scanner.nextLine();
                    if(option.equals("yes")){
                        System.out.println("Enter the new name of the material : ");
                        String name = scanner.nextLine();
                        material1.setName(name);
                    }
                    System.out.println("Do you want to update the quantity of the material? (yes/no)");
                    option = scanner.nextLine();
                    if(option.equals("yes")){
                        System.out.println("Enter the new quantity of this material (in m²): ");
                        double quantity = scanner.nextDouble();
                        material1.setQuantity(quantity);
                    }
                    System.out.println("Do you want to update the unit cost of the material? (yes/no)");
                    option = scanner.nextLine();
                    if(option.equals("yes")){
                        System.out.println("Enter the new unit cost of this material (€/m²): ");
                        double unitCost = scanner.nextDouble();
                        material1.setUnitCost(unitCost);
                    }
                    System.out.println("Do you want to update the cost of transporting the material? (yes/no)");
                    option = scanner.nextLine();
                    if(option.equals("yes")){
                        System.out.println("Enter the new cost of transporting this material (€): ");
                        double transportCost = scanner.nextDouble();
                        material1.setTransportCost(transportCost);
                    }
                    System.out.println("Do you want to update the material quality coefficient? (yes/no)");
                    option = scanner.nextLine();
                    if(option.equals("yes")){
                        System.out.println("Enter the new material quality coefficient (1.0 = standard, > 1.0 = high quality):");
                        double coefficientQuality = scanner.nextDouble();
                        material1.setQualityCoefficient(coefficientQuality);
                    }
                    materialService.update(material1);
                    double totalCost = projectController.totalCostProject(project1);
                    projectController.updateCost(project1,totalCost);
                    System.out.println("Material updated successfully");
                },()->{
                    System.out.println("Material not found");
                    System.out.println("0 -> Cancel");
                });
            },()->{
                System.out.println("Project not found");
                System.out.println("0 -> Cancel");
            });
        }while (project.isEmpty() && id != 0);
    }
    public void deleteMaterial(){
        Optional<Project> project ;
        int id=0;
        do {
            ProjectController projectController = new ProjectController();
            projectController.getAll();
            System.out.println("Select the project for which you want to delete a material (Id) : ");
            id = scanner.nextInt();
            scanner.nextLine();
            project = projectController.getById(id);
            project.ifPresentOrElse(project1 -> {
                List<Material> listMaterial = getAll(project1);
                listMaterial.forEach(System.out::println);
                System.out.println("Select the material to delete (Id) : ");
                int idMaterial = scanner.nextInt();
                scanner.nextLine();
                Optional<Material> material = materialService.getById(idMaterial);
                material.ifPresentOrElse(material1 -> {
                    materialService.delete(material1);
                    double totalCost = projectController.totalCostProject(project1);
                    projectController.updateCost(project1,totalCost);
                    System.out.println("Material deleted successfully");
                },()->{
                    System.out.println("Material not found");
                    System.out.println("0 -> Cancel");
                });
            },()->{
                System.out.println("Project not found");
                System.out.println("0 -> Cancel");
            });
        }while (project.isEmpty() && id != 0);
    }
    public void saveNewMaterial(){
        Optional<Project> project ;
        int id=0;
        do {
            ProjectController projectController = new ProjectController();
            projectController.getAll();
            System.out.println("Select the project for which you want to add a material (Id) : ");
            id = scanner.nextInt();
            scanner.nextLine();
            project = projectController.getById(id);
            project.ifPresentOrElse(project1 -> {
                save(project1);
                double totalCost = projectController.totalCostProject(project1);
                projectController.updateCost(project1,totalCost);
            },()->{
                System.out.println("Project not found");
                System.out.println("0 -> Cancel");
            });
        }while (project.isEmpty() && id != 0);

    }


}
