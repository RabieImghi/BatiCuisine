package controller;

import domain.Labor;
import domain.Project;
import service.LaborService;
import utils.ComponentType;
import utils.Cl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LaborController {
    private final LaborService laborService = new LaborService();
    private final Scanner scanner = new Scanner(System.in);

    public Optional<Labor> save(Project project) {
        System.out.print(Cl.YELLOW + "Do you want to add a labor? (y/n): " + Cl.RESET);

        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.print(Cl.YELLOW + "Enter the labor type (e.g., Basic Worker, Specialist): " + Cl.RESET);
            String name = scanner.nextLine();

            System.out.print(Cl.YELLOW + "Enter the hourly rate for this labor (€/h): " + Cl.RESET);
            double hourlyRate ;
            do {
                while (!scanner.hasNextDouble()){
                    System.out.println(Cl.RED + "Invalid input. Please enter a valid number." + Cl.RESET);
                    scanner.nextLine();
                }
                hourlyRate = scanner.nextDouble();
            }while (hourlyRate <= 0);

            System.out.print(Cl.YELLOW + "Enter the number of hours worked: " + Cl.RESET);
            double hoursWorked ;
            do {
                while (!scanner.hasNextDouble()){
                    System.out.println(Cl.RED + "Invalid input. Please enter a valid number." + Cl.RESET);
                    scanner.nextLine();
                }
                hoursWorked = scanner.nextDouble();
            }while (hoursWorked <= 0);

            System.out.print(Cl.YELLOW + "Enter the productivity factor (1.0 = standard, > 1.0 = high productivity): " + Cl.RESET);
            double workerProductivity;
            do {
                while (!scanner.hasNextDouble()){
                    System.out.println(Cl.RED + "Invalid input. Please enter a valid number." + Cl.RESET);
                    scanner.nextLine();
                }
                workerProductivity = scanner.nextDouble();
            }while (workerProductivity <= 0);


            System.out.print(Cl.YELLOW + "Enter the labor VAT rate (%): " + Cl.RESET);
            double vatRate ;
            do {
                while (!scanner.hasNextDouble()){
                    System.out.println(Cl.RED + "Invalid input. Please enter a valid number." + Cl.RESET);
                    scanner.nextLine();
                }
                vatRate = scanner.nextDouble();
            }while (vatRate <= 0);

            Labor labor = new Labor(name, String.valueOf(ComponentType.LABOR), vatRate, hourlyRate, hoursWorked, workerProductivity, project);

            return laborService.save(labor).map(labor1 -> {
                System.out.println(Cl.GREEN + "Labor added successfully!" + Cl.RESET);
                return labor1;
            }).or(() -> {
                System.out.println(Cl.RED + "Labor not added." + Cl.RESET);
                return Optional.empty();
            });

        } else {
            System.out.println(Cl.RED + "Labor Add Cancelled." + Cl.RESET);
            return Optional.empty();
        }
    }
    public List<Labor> getAll(Project project){
        return laborService.getAll(project);
    }
    public double totalCostLabor(List<Labor> listMaterial){
        return laborService.totalCostLabor(listMaterial);
    }
    public void saveNewLabor(){
        Optional<Project> project ;
        int id=0;
        do {
            ProjectController projectController = new ProjectController();
            projectController.getAll();
            System.out.print("Enter the project id to add a labor: ");
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
            },()-> System.out.println("Project not found"));
        }while (project.isEmpty());
        save(project.get());
    }
    public void manageLabor(){
        boolean exit = false;
        do{
            System.out.println("1 -> Add a labor");
            System.out.println("2 -> Update a labor");
            System.out.println("3 -> Delete a labor");
            System.out.println("4 -> Exit");
            System.out.print("Enter your choice : ");
            String choice = scanner.nextLine();
            switch (choice){
                case "1":
                    saveNewLabor();
                    break;
                case "2":
                    updateLabor();
                    break;
                case "3":
                    deleteLabor();
                    break;
                case "4":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }while (!exit);
    }
    public void updateLabor(){
        Optional<Project> project ;
        int id=0;
        do {
            ProjectController projectController = new ProjectController();
            projectController.getAll();
            System.out.print("Select the project for which you want to update a labor (Id) : ");
            id = scanner.nextInt();
            scanner.nextLine();
            project = projectController.getById(id);
            project.ifPresentOrElse(project1 -> {
                List<Labor> listLabor = getAll(project1);
                if(listLabor.isEmpty()){
                    System.out.println("No labor found for this project");
                }else {
                    listLabor.forEach(System.out::println);
                    System.out.print("Enter the labor id to update: ");
                    int laborId = scanner.nextInt();
                    scanner.nextLine();
                    Optional<Labor> labor = laborService.getById(laborId);
                    labor.ifPresentOrElse(labor1 -> {
                        System.out.println("Do you want to update the labor type? (y/n) : ");
                        String option = scanner.nextLine();
                        if(option.equals("y")){
                            System.out.print("Enter the new labor type: ");
                            String name = scanner.nextLine();
                            labor1.setName(name);
                        }
                        System.out.print("Do you want to update the hourly rate? (y/n) : ");
                        option = scanner.nextLine();
                        if(option.equals("y")){
                            System.out.print("Enter the new hourly rate: ");
                            double hourlyRate ;
                            do {
                                while (!scanner.hasNextDouble()){
                                    System.out.println(Cl.RED + "Invalid input. Please enter a valid number." + Cl.RESET);
                                    scanner.nextLine();
                                }
                                hourlyRate = scanner.nextDouble();
                            }while (hourlyRate <= 0);
                            labor1.setHourlyRate(hourlyRate);
                        }
                        System.out.print("Do you want to update the number of hours worked? (y/n) : ");
                        option = scanner.nextLine();
                        if(option.equals("y")){
                            System.out.print("Enter the new number of hours worked: ");
                            double hoursWorked ;
                            do {
                                while (!scanner.hasNextDouble()){
                                    System.out.println(Cl.RED + "Invalid input. Please enter a valid number." + Cl.RESET);
                                    scanner.nextLine();
                                }
                                hoursWorked = scanner.nextDouble();
                            }while (hoursWorked <= 0);
                            labor1.setHoursWorked(hoursWorked);
                        }
                        System.out.print("Do you want to update the productivity factor? (y/n) : ");
                        option = scanner.nextLine();
                        if(option.equals("y")){
                            System.out.print("Enter the new productivity factor: ");
                            double workerProductivity ;
                            do {
                                while (!scanner.hasNextDouble()){
                                    System.out.println(Cl.RED + "Invalid input. Please enter a valid number." + Cl.RESET);
                                    scanner.nextLine();
                                }
                                workerProductivity = scanner.nextDouble();
                            }while (workerProductivity <= 0);
                            labor1.setWorkerProductivity(workerProductivity);
                        }
                        laborService.update(labor1);
                        double totalCost = projectController.totalCostProject(project1);
                        projectController.updateCost(project1,totalCost);
                        project1.setTotalCost(totalCost);
                        QuoteController quoteController = new QuoteController();
                        quoteController.amountEstimateUpdate(project1);
                    },()-> System.out.println("Labor not found"));
                }
            },()-> System.out.println("Project not found"));
        }while (project.isEmpty());
    }

    public void deleteLabor(){
        Optional<Project> project ;
        int id=0;
        do {
            ProjectController projectController = new ProjectController();
            projectController.getAll();
            System.out.print("Select the project for which you want to delete a labor (Id) : ");
            id = scanner.nextInt();
            scanner.nextLine();
            project = projectController.getById(id);
            project.ifPresentOrElse(project1 -> {
                List<Labor> listLabor = getAll(project1);
                if(listLabor.isEmpty()){
                    System.out.println("No labor found for this project");
                }else {
                    listLabor.forEach(System.out::println);
                    System.out.print("Enter the labor id to delete: ");
                    int laborId = scanner.nextInt();
                    scanner.nextLine();
                    Optional<Labor> labor = laborService.getById(laborId);
                    labor.ifPresentOrElse(labor1 -> {
                        laborService.delete(labor1);
                        double totalCost = projectController.totalCostProject(project1);
                        projectController.updateCost(project1,totalCost);
                        project1.setTotalCost(totalCost);
                        QuoteController quoteController = new QuoteController();
                        quoteController.amountEstimateUpdate(project1);
                    },()-> System.out.println("Labor not found"));
                }
            },()-> System.out.println("Project not found"));
        }while (project.isEmpty());
    }
}
