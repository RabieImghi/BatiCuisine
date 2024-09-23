package controller;

import domain.*;
import service.ClientService;
import service.ProjectService;
import utils.Cl;
import utils.Menu;
import utils.ProjectStatus;

import java.util.*;

public class ProjectController {
    private final ProjectService projectService = new ProjectService();
    private final ClientController clientController = new ClientController();
    private final MaterialController materialController = new MaterialController();
    private final LaborController laborController = new LaborController();
    private final QuoteController quoteController = new QuoteController();

    private final Scanner scanner = new Scanner(System.in);
    public void manageProject(){
        boolean exit = false;
        do{
            Menu.showProjectManageMenu();
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
                    System.out.println(Cl.GREEN + "Exiting..." + Cl.RESET);
                    break;
                default:
                    System.out.println(Cl.RED + "Invalid option, please choose a valid number." + Cl.RESET);
                    break;
            }
        }while (!exit);
    }
    public void save() {
        Optional<Client> clients = getClient();

        clients.ifPresent(client -> {
            System.out.println(Cl.YELLOW + "\n================================================" + Cl.RESET);
            System.out.println(Cl.GREEN + "Client Found !" + Cl.RESET);
            System.out.println(Cl.CYAN + "Client Name: " + Cl.RESET + client.getName());
            System.out.println(Cl.CYAN + "Client Address: " + Cl.RESET + client.getAddress());
            System.out.println(Cl.CYAN + "Client Phone: " + Cl.RESET + client.getPhone());
            String isProfessional = client.isProfessional() ? Cl.GREEN + "Professional" + Cl.RESET : Cl.RED + "Not Professional" + Cl.RESET;
            System.out.println(Cl.CYAN + "Client Status: " + Cl.RESET + isProfessional);
            System.out.println(Cl.YELLOW + "\nDo you want to add a project for this client? (y/n)" + Cl.RESET);
            String option = scanner.nextLine();
            if (option.equalsIgnoreCase("y")) {
                System.out.print(Cl.YELLOW + "Enter the Project Name: " + Cl.RESET);
                String name = scanner.nextLine();
                Project project = new Project(name, client);
                projectService.save(project).ifPresentOrElse(project1 -> {
                    System.out.println(Cl.GREEN + "Project added successfully!" + Cl.RESET);
                    saveMaterial(project1);
                    saveLabor(project1);
                    System.out.println("--- " + Cl.CYAN + "Calculation of total cost" + Cl.RESET + " ---");
                    System.out.print(Cl.YELLOW + "Would you like to apply a profit margin to the project? (y/n): " + Cl.RESET);
                    String profit = scanner.nextLine();

                    if (profit.equalsIgnoreCase("y")) {
                        System.out.print(Cl.YELLOW + "Enter the profit margin percentage (%): " + Cl.RESET);
                        double profitMargin = scanner.nextDouble();
                        scanner.nextLine();
                        project1.setProfitMargin(profitMargin);
                        projectService.updateProfitMargin(project1, profitMargin);
                    }
                    System.out.println(Cl.CYAN + "Cost calculation in progress..." + Cl.RESET);
                    calculateTotalCost(project1, 1);

                }, () -> System.out.println(Cl.RED + "Project not added" + Cl.RESET));
            } else {
                System.out.println(Cl.RED + "Project addition cancelled." + Cl.RESET);
            }
        });
    }

    public void calculateTotalCost(Project project, int isRunning) {
        Optional<Client> clients = clientController.findById(project.getClient().getId());
        clients.ifPresent(client -> {
            double totalCostMaterial = 0;
            double totalCostLabor = 0;
            double totalCost = 0;
            System.out.println(Cl.YELLOW + "================================================" + Cl.RESET);
            System.out.println(Cl.CYAN + "Client Information:" + Cl.RESET);
            System.out.println(Cl.YELLOW + "================================================" + Cl.RESET);
            System.out.printf("%-20s | %-20s", Cl.CYAN + "Name" + Cl.RESET, client.getName());
            System.out.printf("\n%-20s | %-20s", Cl.CYAN + "Address: " + Cl.RESET, client.getAddress());
            System.out.printf("\n%-20s | %-20s", Cl.CYAN + "Phone: " + Cl.RESET, client.getPhone());
            System.out.printf("\n%-20s | %-20s", Cl.CYAN + "Status: " + Cl.RESET, client.isProfessional() ? Cl.GREEN + "Professional" + Cl.RESET : Cl.RED + "Not Professional" + Cl.RESET);
            System.out.println(Cl.YELLOW + "\n\n================================================" + Cl.RESET);
            System.out.println(Cl.CYAN + "Project Information:" + Cl.RESET);
            System.out.println(Cl.YELLOW + "================================================" + Cl.RESET);
            System.out.printf("%-20s | %-20s", Cl.CYAN + "Project Name" + Cl.RESET, project.getProjectName());
            System.out.printf("\n%-20s | %-20s", Cl.CYAN + "Project Status" + Cl.RESET, project.getProjectStatus());
            System.out.printf("\n%-20s | %-20s", Cl.CYAN + "Profit Margin" + Cl.RESET, project.getProfitMargin() + " %");


            System.out.println(Cl.CYAN + "\n\n================================================" + Cl.RESET);
            System.out.println(Cl.YELLOW + "Materials :" + Cl.RESET);
            List<Material> listMaterial = materialController.getAll(project);
            double vatRate = listMaterial.get(0).getVatRate();
            double vat = (vatRate / 100) + 1;
            totalCostMaterial = materialController.totalCostMaterial(listMaterial);
            if(listMaterial.isEmpty()){
                System.out.println(Cl.RED + "No materials found" + Cl.RESET);
            }else {
                System.out.printf(Cl.CYAN + "%-15s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s%n" + Cl.RESET,
                        "Material Name", "Type", "VAT Rate", "Unit Cost", "Quantity",
                        "Transport Cost", "Quality Coefficient");
                System.out.println(Cl.CYAN + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + Cl.RESET);

                listMaterial.forEach(material -> {
                    System.out.printf(Cl.YELLOW+"%-15s "+ Cl.CYAN+"|"+ Cl.YELLOW+" %-20s "+ Cl.CYAN+"|"+ Cl.YELLOW+" %-20.2f "+ Cl.CYAN+"|"+ Cl.YELLOW+" %-20.2f "+ Cl.CYAN+"|"+ Cl.YELLOW+" %-20.2f "+ Cl.CYAN+"|"+ Cl.YELLOW+" %-20.2f "+ Cl.CYAN+"|"+ Cl.YELLOW+" %-20.2f%n"+ Cl.RESET,
                            material.getName(), material.getComponentType(), material.getVatRate(),
                            material.getUnitCost(), material.getQuantity(), material.getTransportCost(),
                            material.getQualityCoefficient());
                    System.out.println(Cl.CYAN + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + Cl.RESET);

                });
                System.out.println(Cl.CYAN + "**Total cost of materials before VAT: " + Cl.RESET + String.format("%.2f", totalCostMaterial) + " €");
                System.out.println(Cl.GREEN + "**Total cost of materials with VAT ("+vatRate+") " + Cl.RESET + String.format("%.2f", totalCostMaterial * vat) + " €");
            }

            List<Labor> listLabor = laborController.getAll(project);
            System.out.println(Cl.CYAN + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + Cl.RESET);
            System.out.println(Cl.YELLOW + "\n\nLabor : " + Cl.RESET);
            if (listLabor.isEmpty()) {
                System.out.println(Cl.RED + "No labor found" + Cl.RESET);
            }else {
                System.out.printf(Cl.CYAN + "%-15s | %-20s | %-20s | %-20s | %-20s | %-20s%n" + Cl.RESET,
                        "Labor Type", "Type", "VAT Rate", "Hourly Rate", "Hours Worked",
                        "Productivity");
                System.out.println(Cl.CYAN + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + Cl.RESET);

                totalCostLabor = laborController.totalCostLabor(listLabor);

                listLabor.forEach(labor -> {
                    System.out.printf(Cl.YELLOW+"%-15s "+ Cl.CYAN+"|"+ Cl.YELLOW+" %-20s "+ Cl.CYAN+"| "+ Cl.YELLOW+"%-20.2f "+ Cl.CYAN+"| "+ Cl.YELLOW+"%-20.2f "+ Cl.CYAN+"| "+ Cl.YELLOW+"%-20.2f "+ Cl.CYAN+"| "+ Cl.YELLOW+"%-20.2f%n"+ Cl.RESET,
                            labor.getName(), labor.getComponentType(), labor.getVatRate(),
                            labor.getHourlyRate(), labor.getHoursWorked(), labor.getWorkerProductivity());
                    System.out.println(Cl.CYAN + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + Cl.RESET);
                });

                System.out.println(Cl.CYAN + "**Total labor cost before VAT: " + Cl.RESET + String.format("%.2f", totalCostLabor) + " €");
                System.out.println(Cl.GREEN + "**Total labor cost with VAT (" + vatRate + "%): " + Cl.RESET + String.format("%.2f", totalCostLabor * vat) + " €");
                System.out.println(Cl.CYAN + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + Cl.RESET);
            }

            totalCost = (totalCostLabor * vat) + (totalCostMaterial * vat);
            double totalCostMargin = totalCost * project.getProfitMargin() / 100;
            System.out.println(Cl.GREEN + "\n\n3. Total cost before margin: " + Cl.RESET + String.format("%.2f", totalCost) + " €");
            System.out.println(Cl.GREEN + "4. Profit margin (" + project.getProfitMargin() + "%): " + Cl.RESET + String.format("%.2f", totalCostMargin) + " €");

            double finalTotalCost = totalCostMargin + totalCost;
            System.out.println(Cl.RED + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + Cl.RESET);
            System.out.println(Cl.RED + "**Final total cost of the project: " + Cl.RESET + String.format("%.2f", finalTotalCost) + " €");
            System.out.println(Cl.RED + "-----------------------------------------------------------------------------------------------------------------------------------------------------------------" + Cl.RESET);
            if (isRunning == 1) {
                updateCost(project, finalTotalCost);

                System.out.print(Cl.YELLOW + "Would you like to add a quote? (y/n): " + Cl.RESET);
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
            System.out.println(Cl.RED + "No project found" + Cl.RESET);
        } else {
            System.out.printf(Cl.CYAN + "\n%-20s |%-20s  | %-20s | %-20s | %-20s | %-20s%n" + Cl.RESET,
                    "Project Id", "Client Name", "Project Name", "Project Status", "Profit Margin", "Total Cost");
            System.out.println(Cl.CYAN + "_______________________________________________________________________________________________________________________________" + Cl.RESET);
            projectList.forEach(project -> {
                String status="";
                switch (project.getProjectStatus()) {
                    case IN_PROGRESS:
                        status = Cl.GREEN + "In Progress" + Cl.RESET;
                        break;
                    case COMPLETED:
                        status = Cl.PURPLE + "Completed" + Cl.RESET;
                        break;
                    case CANCELED:
                        status = Cl.RED + "Canceled" + Cl.RESET;
                        break;
                }
                System.out.printf("%-20d | %-20s | %-20s | %-20s | %-20s | %-20s\n",
                        project.getId(), project.getClient().getName(), project.getProjectName(), status,
                        project.getProfitMargin() + " %", project.getTotalCost() + " €");
                System.out.println(Cl.YELLOW + "-------------------------------------------------------------------------------------------------------------------------------" + Cl.RESET);
            });
        }
    }
    public void getProjectDetail(){
        getAll();
        System.out.print(Cl.CYAN + "Do you want to see the details of a project? (y/n): " + Cl.RESET);
        String option = scanner.nextLine();

        if (option.equalsIgnoreCase("y")) {
            System.out.print(Cl.CYAN + "Enter the project id: " + Cl.RESET);
            int id = scanner.nextInt();
            scanner.nextLine();
            Optional<Project> project = projectService.getById(id);
            project.ifPresentOrElse(
                    project1 -> calculateTotalCost(project1, 0),
                    () -> System.out.println(Cl.RED + "Project not found" + Cl.RESET)
            );
        }
    }

    public void deleteProject(){
        getAll();
        System.out.print(Cl.CYAN+"Enter the project id to delete : "+ Cl.RESET);
        int id ;
        do {
            try {
                id = scanner.nextInt();
            }catch (InputMismatchException e){
                System.out.println(Cl.RED+"Invalid id"+ Cl.RESET);
                scanner.nextLine();
                return;
            }
        }while (id<=0);
        scanner.nextLine();
        Optional<Project> project = projectService.getById(id);
        project.ifPresentOrElse(project1 -> {
            System.out.print(Cl.RED+"Are you sure you want to delete the project? (y/n) : "+ Cl.RESET);
            String option = scanner.nextLine();
            if (option.equals("y")){
                projectService.delete(project1);
                System.out.println(Cl.GREEN+"Project deleted with success"+ Cl.RESET);
            }else {
                System.out.println(Cl.RED+"Project not deleted"+ Cl.RESET);
            }
        },()-> System.out.println(Cl.RED+"Project not found"+ Cl.RESET));
    }
    public void updateProject(){
        getAll();
        System.out.print("Enter the project id to update : ");
        int id ;
        do {
            try {
                id = scanner.nextInt();
            }catch (NumberFormatException e){
                System.out.println("Invalid id");
                scanner.nextLine();
                return;
            }
        }while (id<=0);
        scanner.nextLine();
        Optional<Project> project = projectService.getById(id);
        project.ifPresentOrElse(project1 -> {
            String option;
            do {
                Menu.showUpdateProjectMenu();
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
                        scanner.nextLine();
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
    public void update(Project project){
        projectService.update(project);
    }

}
