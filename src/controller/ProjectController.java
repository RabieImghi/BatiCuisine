package controller;

import domain.*;
import service.ClientService;
import service.ProjectService;
import utils.ProjectStatus;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ProjectController {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";
    private final ProjectService projectService = new ProjectService();
    private final ClientService clientService = new ClientService();
    private final ClientController clientController = new ClientController();
    private final MaterialController materialController = new MaterialController();
    private final LaborController laborController = new LaborController();
    private final QuoteController quoteController = new QuoteController();

    private final Scanner scanner = new Scanner(System.in);
    public void manageProject(){
        boolean exit = false;
        do{
            System.out.println(BLUE + "\n1 : Add Project" + RESET);
            System.out.println(BLUE + "2 : Delete Project" + RESET);
            System.out.println(BLUE + "3 : Update Project" + RESET);
            System.out.println(BLUE + "4 : Exit" + RESET);
            System.out.print(YELLOW + "Choose an option: " + RESET);
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    save();
                    break;
                case "2":
                    deleteProject();
                    break;
                case "3":
                    updateProject();
                    break;
                case "4":
                    exit = true;
                    System.out.println(GREEN + "Exiting..." + RESET);
                    break;
                default:
                    System.out.println(RED + "Invalid option, please choose a valid number." + RESET);
                    break;
            }
        }while (!exit);
    }
    public void save() {
        Optional<Client> clients = getClient();

        clients.ifPresent(client -> {
            System.out.println(CYAN + "Client Name: " + RESET + client.getName());
            System.out.println(CYAN + "Client Address: " + RESET + client.getAddress());
            System.out.println(CYAN + "Client Phone: " + RESET + client.getPhone());
            String isProfessional = client.isProfessional() ? GREEN + "Professional" + RESET : RED + "Not Professional" + RESET;
            System.out.println(CYAN + "Client Status: " + RESET + isProfessional);
            System.out.println(YELLOW + "\nDo you want to add a project for this client? (y/n)" + RESET);
            String option = scanner.nextLine();
            if (option.equalsIgnoreCase("y")) {
                System.out.print(YELLOW + "Enter the Project Name: " + RESET);
                String name = scanner.nextLine();
                Project project = new Project(name, client);
                projectService.save(project).ifPresentOrElse(project1 -> {
                    System.out.println(GREEN + "Project added successfully!" + RESET);
                    saveMaterial(project1);
                    saveLabor(project1);
                    System.out.println("--- " + CYAN + "Calculation of total cost" + RESET + " ---");
                    System.out.print(YELLOW + "Would you like to apply a profit margin to the project? (y/n): " + RESET);
                    String profit = scanner.nextLine();

                    if (profit.equalsIgnoreCase("y")) {
                        System.out.print(YELLOW + "Enter the profit margin percentage (%): " + RESET);
                        double profitMargin = scanner.nextDouble();
                        scanner.nextLine();
                        project1.setProfitMargin(profitMargin);
                        projectService.updateProfitMargin(project1, profitMargin);
                    }
                    System.out.println(CYAN + "Cost calculation in progress..." + RESET);
                    calculateTotalCost(project1, 1);

                }, () -> System.out.println(RED + "Project not added" + RESET));
            } else {
                System.out.println(RED + "Project addition cancelled." + RESET);
            }
        });
    }

    public void calculateTotalCost(Project project, int isRunning) {
        Optional<Client> clients = clientController.findById(project.getClient().getId());
        clients.ifPresent(client -> {
            double totalCostMaterial = 0;
            double totalCostLabor = 0;
            double totalCost = 0;
            System.out.println(YELLOW + "================================================" + RESET);
            System.out.println(CYAN + "Client Information:" + RESET);
            System.out.println(YELLOW + "================================================" + RESET);
            System.out.printf("%-20s | %-20s", CYAN + "Name" + RESET, client.getName());
            System.out.printf("\n%-20s | %-20s",CYAN + "Address: " + RESET, client.getAddress());
            System.out.printf("\n%-20s | %-20s",CYAN + "Phone: " + RESET, client.getPhone());


            System.out.println(CYAN + "\n\n================================================" + RESET);
            System.out.println(YELLOW + "Materials :" + RESET);
            List<Material> listMaterial = materialController.getAll(project);
            double vatRate = listMaterial.get(0).getVatRate();
            double vat = (vatRate / 100) + 1;
            totalCostMaterial = materialController.totalCostMaterial(listMaterial);
            if(listMaterial.isEmpty()){
                System.out.println(RED + "No materials found" + RESET);
            }else {
                System.out.printf(CYAN + "%-15s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s%n" + RESET,
                        "Material Name", "Type", "VAT Rate", "Unit Cost", "Quantity",
                        "Transport Cost", "Quality Coefficient");
                System.out.println(CYAN + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + RESET);

                listMaterial.forEach(material -> {
                    System.out.printf(YELLOW+"%-15s "+CYAN+"|"+YELLOW+" %-20s "+CYAN+"|"+YELLOW+" %-20.2f "+CYAN+"|"+YELLOW+" %-20.2f "+CYAN+"|"+YELLOW+" %-20.2f "+CYAN+"|"+YELLOW+" %-20.2f "+CYAN+"|"+YELLOW+" %-20.2f%n"+RESET,
                            material.getName(), material.getComponentType(), material.getVatRate(),
                            material.getUnitCost(), material.getQuantity(), material.getTransportCost(),
                            material.getQualityCoefficient());
                    System.out.println(CYAN + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + RESET);

                });
                System.out.println(CYAN + "**Total cost of materials before VAT: " + RESET + String.format("%.2f", totalCostMaterial) + " €");
                System.out.println(GREEN + "**Total cost of materials with VAT ("+vatRate+") " + RESET + String.format("%.2f", totalCostMaterial * vat) + " €");
            }

            List<Labor> listLabor = laborController.getAll(project);
            System.out.println(CYAN + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + RESET);
            System.out.println(YELLOW + "\n\nLabor : " + RESET);
            if (listLabor.isEmpty()) {
                System.out.println(RED + "No labor found" + RESET);
            }else {
                System.out.printf(CYAN + "%-15s | %-20s | %-20s | %-20s | %-20s | %-20s%n" + RESET,
                        "Labor Type", "Type", "VAT Rate", "Hourly Rate", "Hours Worked",
                        "Productivity");
                System.out.println(CYAN + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + RESET);

                totalCostLabor = laborController.totalCostLabor(listLabor);

                listLabor.forEach(labor -> {
                    System.out.printf(YELLOW+"%-15s "+CYAN+"|"+YELLOW+" %-20s "+CYAN+"| "+YELLOW+"%-20.2f "+CYAN+"| "+YELLOW+"%-20.2f "+CYAN+"| "+YELLOW+"%-20.2f "+CYAN+"| "+YELLOW+"%-20.2f%n"+RESET,
                            labor.getName(), labor.getComponentType(), labor.getVatRate(),
                            labor.getHourlyRate(), labor.getHoursWorked(), labor.getWorkerProductivity());
                    System.out.println(CYAN + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + RESET);
                });

                System.out.println(CYAN + "**Total labor cost before VAT: " + RESET + String.format("%.2f", totalCostLabor) + " €");
                System.out.println(GREEN + "**Total labor cost with VAT (" + vatRate + "%): " + RESET + String.format("%.2f", totalCostLabor * vat) + " €");
                System.out.println(CYAN + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + RESET);
            }

            totalCost = (totalCostLabor * vat) + (totalCostMaterial * vat);
            double totalCostMargin = totalCost * project.getProfitMargin() / 100;
            System.out.println(GREEN + "\n\n3. Total cost before margin: " + RESET + String.format("%.2f", totalCost) + " €");
            System.out.println(GREEN + "4. Profit margin (" + project.getProfitMargin() + "%): " + RESET + String.format("%.2f", totalCostMargin) + " €");

            double finalTotalCost = totalCostMargin + totalCost;
            System.out.println(RED + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + RESET);
            System.out.println(RED + "**Final total cost of the project: " + RESET + String.format("%.2f", finalTotalCost) + " €");
            System.out.println(RED + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + RESET);
            if (isRunning == 1) {
                updateCost(project, finalTotalCost);

                System.out.print(YELLOW + "Would you like to add a quote? (y/n): " + RESET);
                String quote = scanner.nextLine();
                if (quote.equalsIgnoreCase("y")) {
                    quoteController.save(project, finalTotalCost);
                }
            }
        });
    }
    public void updateCost(Project project,double cost){
        projectService.updateProfitCost(project,cost);
    }
    public double totalCostProject(Project project){
        List<Material> listMaterial = materialController.getAll(project);
        double vat = (listMaterial.get(0).getVatRate()/100)+1;
        double profitMargin = project.getProfitMargin();
        double totalCostMaterial= materialController.totalCostMaterial(listMaterial);
        List<Labor> listLabor = laborController.getAll(project);
        double totalCostLabor = laborController.totalCostLabor(listLabor);
        double totalCost = (totalCostLabor * vat) + (totalCostMaterial * vat );
        double totalCostProfit = totalCost * profitMargin/100;
        return totalCostProfit+totalCost;
    }
    public void saveMaterial(Project project){
        Optional<Material> optionalMaterial ;
        do{
            optionalMaterial =  materialController.save(project);
        }while (optionalMaterial.isPresent());
    }
    public void saveLabor(Project project){
        Optional<Labor> optionalLabor;
        do{
            optionalLabor =  laborController.save(project);
        }while (optionalLabor.isPresent());
    }
    public Optional<Client> getClient() {
        System.out.println("1 : Search Client ");
        System.out.println("2 : Add Client ");
        String option;
        do {
            option = scanner.nextLine();
            switch (option) {
                case "1": {
                    return clientController.getClient();
                }
                case "2": {
                    return clientController.save();
                }
                default:
                    System.out.println("Invalid option");
                    return Optional.empty();
            }
        } while (!option.equals("1") && !option.equals("2"));
    }
    public Optional<Project> getById(int id){
        return projectService.getById(id);
    }

    public void getAll() {
        List<Project> projectList = projectService.getAll();
        if (projectList.isEmpty()) {
            System.out.println(RED + "No project found" + RESET);
        } else {
            System.out.printf(CYAN + "\n%-20s |%-20s  | %-20s | %-20s | %-20s | %-20s%n" + RESET,
                    "Project Id", "Client Name", "Project Name", "Project Status", "Profit Margin", "Total Cost");
            System.out.println(CYAN + "_______________________________________________________________________________________________________________________________" + RESET);
            projectList.forEach(project -> {
                String status = project.getProjectStatus() == ProjectStatus.IN_PROGRESS ? GREEN + "In Progress" + RESET : PURPLE + "Completed" + RESET;
                System.out.printf("%-20d | %-20s | %-20s | %-20s | %-20s | %-20s\n",
                        project.getId(), project.getClient().getName(), project.getProjectName(), status,
                        project.getProfitMargin() + " %", project.getTotalCost() + " €");
                System.out.println(YELLOW + "-------------------------------------------------------------------------------------------------------------------------------" + RESET);
            });
            System.out.print(CYAN + "Do you want to see the details of a project? (y/n): " + RESET);
            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("y")) {
                System.out.print(CYAN + "Enter the project id: " + RESET);
                int id = scanner.nextInt();
                scanner.nextLine();
                Optional<Project> project = projectService.getById(id);
                project.ifPresentOrElse(
                        project1 -> calculateTotalCost(project1, 0),
                        () -> System.out.println(RED + "Project not found" + RESET)
                );
            }
        }
    }

    public void deleteProject(){
        getAll();
        System.out.print("Enter the project id to delete : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Optional<Project> project = projectService.getById(id);
        project.ifPresentOrElse(project1 -> {
            System.out.println("Are you sure you want to delete the project? (y/n) : ");
            String option = scanner.nextLine();
            if (option.equals("y")){
                projectService.delete(project1);
                System.out.println("Project deleted with success");
            }
        },()-> System.out.println("Project not found"));
    }
    public void updateProject(){
        getAll();
        System.out.print("Enter the project id to update : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<Project> project = projectService.getById(id);
        project.ifPresentOrElse(project1 -> {
            String option;
            do {
                System.out.println("1 : Update Project Name");
                System.out.println("2 : Update Project Status");
                System.out.println("3 : Update Profit Margin");
                System.out.println("4 : Exit");
                option = scanner.nextLine();
                switch (option){
                    case "1": {
                        System.out.print("Enter the new project name : ");
                        String name = scanner.nextLine();
                        project1.setProjectName(name);
                        projectService.update(project1);
                        break;
                    }
                    case "2": {
                        String status;
                        do {
                            System.out.println("Enter the new project status (In Progress/Completed) : ");
                            System.out.println("1 : In Progress");
                            System.out.println("2 : Completed");
                            System.out.println("3 : Cancel");
                            System.out.println("4 : Exit");
                            status = scanner.nextLine();
                            switch (status) {
                                case "1":
                                    project1.setProjectStatus(ProjectStatus.IN_PROGRESS);
                                    break;
                                case "2":
                                    project1.setProjectStatus(ProjectStatus.COMPLETED);
                                    break;
                                case "3":
                                    project1.setProjectStatus(ProjectStatus.CANCELED);
                                    break;
                                default:
                                    System.out.println("Invalid option");
                            }
                        }while (!status.equals("1") && !status.equals("2") && !status.equals("3") && !status.equals("4"));
                        projectService.update(project1);
                        break;
                    }
                    case "3": {
                        System.out.print("Enter the new profit margin : ");
                        double profitMargin = scanner.nextDouble();
                        project1.setProfitMargin(profitMargin);
                        projectService.update(project1);
                        double totalCost = totalCostProject(project1);
                        updateCost(project1,totalCost);
                        break;
                    }
                    case "4": break;
                    default:
                        System.out.println("Invalid option");
                }
            }while (!option.equals("4"));

        },()-> System.out.println("Project not found"));
    }

}
