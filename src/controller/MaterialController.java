package controller;

import domain.Material;
import domain.Project;
import service.MaterialService;
import utils.ComponentType;
import utils.Cl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MaterialController {

    private final MaterialService materialService = new MaterialService();
    private final Scanner scanner = new Scanner(System.in);
    public Optional<Material> save(Project project) {
        System.out.print(Cl.YELLOW + "Do you want to add a material? (y/n): " + Cl.RESET);
        String option = scanner.nextLine();

        if (option.equalsIgnoreCase("y")) {
            System.out.print(Cl.YELLOW + "Enter the material name: " + Cl.RESET);
            String name = scanner.nextLine();

            System.out.print(Cl.YELLOW + "Enter the quantity of this material (in m²): " + Cl.RESET);
            double quantity;
            do {
                System.out.print(Cl.YELLOW + "Enter the quantity of this material (in m²): " + Cl.RESET);
                while (!scanner.hasNextDouble()) {
                    System.out.println(Cl.RED + "Invalid quantity" + Cl.RESET);
                    scanner.next();
                }
                quantity = scanner.nextDouble();
            } while (quantity <= 0);
            System.out.print(Cl.YELLOW + "Enter the unit cost of this material (€/m²): " + Cl.RESET);
            double unitCost;
            do {
                System.out.print(Cl.YELLOW + "Enter the unit cost of this material (€/m²): " + Cl.RESET);
                while (!scanner.hasNextDouble()) {
                    System.out.println(Cl.RED + "Invalid unit cost" + Cl.RESET);
                    scanner.next();
                }
                unitCost = scanner.nextDouble();
            } while (unitCost <= 0);

            System.out.print(Cl.YELLOW + "Enter the cost of transporting this material (€): " + Cl.RESET);
            double transportCost;
            do {
                System.out.print(Cl.YELLOW + "Enter the cost of transporting this material (€): " + Cl.RESET);
                while (!scanner.hasNextDouble()) {
                    System.out.println(Cl.RED + "Invalid transport cost" + Cl.RESET);
                    scanner.next();
                }
                transportCost = scanner.nextDouble();
            } while (transportCost <= 0);

            System.out.print(Cl.YELLOW + "Enter the material quality coefficient (1.0 = standard, > 1.0 = high quality): " + Cl.RESET);
            double coefficientQuality;
            do {
                System.out.print(Cl.YELLOW + "Enter the material quality coefficient (1.0 = standard, > 1.0 = high quality): " + Cl.RESET);
                while (!scanner.hasNextDouble()) {
                    System.out.println(Cl.RED + "Invalid coefficient quality" + Cl.RESET);
                    scanner.next();
                }
                coefficientQuality = scanner.nextDouble();
            } while (coefficientQuality < 1 || coefficientQuality > 2);

            System.out.print(Cl.YELLOW + "Enter the material VAT rate (%): " + Cl.RESET);
            double vatRate;
            do {
                System.out.print(Cl.YELLOW + "Enter the material VAT rate (%): " + Cl.RESET);
                while (!scanner.hasNextDouble()) {
                    System.out.println(Cl.RED + "Invalid VAT rate" + Cl.RESET);
                    scanner.next();
                }
                vatRate = scanner.nextDouble();
            } while (vatRate < 0 || vatRate > 100);

            scanner.nextLine();

            Material material = new Material(name, String.valueOf(ComponentType.MATERIAL), vatRate, unitCost, quantity, transportCost, coefficientQuality, project);
            return materialService.save(material).map(material1 -> {
                System.out.println(Cl.GREEN + "Material added successfully!" + Cl.RESET);
                return material1;
            }).or(() -> {
                System.out.println(Cl.RED + "Material not added" + Cl.RESET);
                return Optional.empty();
            });

        } else {
            System.out.println(Cl.RED + "Material Add Cancelled" + Cl.RESET);
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
                    System.out.println("Do you want to update the name of the material? (y/n)");
                    String option = scanner.nextLine();
                    if(option.equals("y")){
                        System.out.println("Enter the new name of the material : ");
                        String name = scanner.nextLine();
                        material1.setName(name);
                    }
                    System.out.println("Do you want to update the quantity of the material? (y/n)");
                    option = scanner.nextLine();
                    if(option.equals("y")){
                        System.out.println("Enter the new quantity of this material (in m²): ");
                        double quantity;
                        do {
                            System.out.print("Enter the new quantity of this material (in m²): ");
                            while (!scanner.hasNextDouble()) {
                                System.out.println("Invalid quantity");
                                scanner.next();
                            }
                            quantity = scanner.nextDouble();
                        } while (quantity <= 0);
                        material1.setQuantity(quantity);
                    }
                    System.out.println("Do you want to update the unit cost of the material? (y/n)");
                    option = scanner.nextLine();
                    if(option.equals("y")){
                        System.out.println("Enter the new unit cost of this material (€/m²): ");
                        double unitCost ;
                        do {
                            System.out.print("Enter the new unit cost of this material (€/m²): ");
                            while (!scanner.hasNextDouble()) {
                                System.out.println("Invalid unit cost");
                                scanner.next();
                            }
                            unitCost = scanner.nextDouble();
                        } while (unitCost <= 0);
                        material1.setUnitCost(unitCost);
                    }
                    System.out.println("Do you want to update the cost of transporting the material? (y/n)");
                    option = scanner.nextLine();
                    if(option.equals("y")){
                        System.out.println("Enter the new cost of transporting this material (€): ");
                        double transportCost ;
                        do {
                            System.out.print("Enter the new cost of transporting this material (€): ");
                            while (!scanner.hasNextDouble()) {
                                System.out.println("Invalid transport cost");
                                scanner.next();
                            }
                            transportCost = scanner.nextDouble();
                        } while (transportCost <= 0);
                        material1.setTransportCost(transportCost);
                    }
                    System.out.println("Do you want to update the material quality coefficient? (y/n)");
                    option = scanner.nextLine();
                    if(option.equals("y")){
                        System.out.println("Enter the new material quality coefficient (1.0 = standard, > 1.0 = high quality):");
                        double coefficientQuality ;
                        do {
                            System.out.print("Enter the new material quality coefficient (1.0 = standard, > 1.0 = high quality): ");
                            while (!scanner.hasNextDouble()) {
                                System.out.println("Invalid coefficient quality");
                                scanner.next();
                            }
                            coefficientQuality = scanner.nextDouble();
                        } while (coefficientQuality < 1 || coefficientQuality > 2);
                        material1.setQualityCoefficient(coefficientQuality);
                    }
                    materialService.update(material1);
                    double totalCost = projectController.totalCostProject(project1);
                    projectController.updateCost(project1,totalCost);
                    project1.setTotalCost(totalCost);
                    QuoteController quoteController = new QuoteController();
                    quoteController.amountEstimateUpdate(project1);
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
                    project1.setTotalCost(totalCost);
                    QuoteController quoteController = new QuoteController();
                    quoteController.amountEstimateUpdate(project1);
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
                project1.setTotalCost(totalCost);
                QuoteController quoteController = new QuoteController();
                quoteController.amountEstimateUpdate(project1);
            },()->{
                System.out.println("Project not found");
                System.out.println("0 -> Cancel");
            });
        }while (project.isEmpty() && id != 0);

    }


}
