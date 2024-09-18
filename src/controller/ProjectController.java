package controller;

import domain.*;
import service.ClientService;
import service.ProjectService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ProjectController {

    private final ProjectService projectService = new ProjectService();
    private final ClientService clientService = new ClientService();
    private final ClientController clientController = new ClientController();
    private final MaterialController materialController = new MaterialController();
    private final LaborController laborController = new LaborController();
    private final QuoteController quoteController = new QuoteController();

    private final Scanner scanner = new Scanner(System.in);
    public void save() {
        Optional<Client> clients = getClient();
        clients.ifPresent(client ->{
            System.out.println("Client Name : " + client.getName());
            System.out.println("Client Address : " + client.getAddress());
            System.out.println("Client Phone : " + client.getPhone());
            String isProfessional = client.isProfessional() ? "Professional" : "Not Professional";
            System.out.println("Client is Professional : " + isProfessional);
            System.out.println("\nDo you want to add a project for this client? (yes/no)");
            String option = scanner.nextLine();
            if(option.equals("yes")){
                System.out.println("Project Name");
                String name = scanner.nextLine();
                Project project = new Project(name, client);
                projectService.save(project).ifPresentOrElse(project1 -> {
                    System.out.println("Project added successfully");
                    saveMaterial(project1);
                    saveLabor(project1);
                    String def = scanner.nextLine();
                    System.out.println("--- Calculation of total cost ---");
                    System.out.print("Would you like to apply VAT to the project? (y/n) : ");
                    String vat = scanner.nextLine();
                    if(vat.equals("y")){
                        materialController.updateVAT(project1);
                    }
                    System.out.print("Would you like to apply a profit margin to the project? (y/n) : ");
                    String profit = scanner.nextLine();
                    if(profit.equals("y")){
                        System.out.println("Enter the profit margin percentage (%) : ");
                        double profitMargin = scanner.nextDouble();
                        projectService.updateProfitMargin(project1, profitMargin);
                        project1.setProfitMargin(profitMargin);
                    }
                    System.out.println("Cost calculation in progress...");
                    calculateTotalCost(project1,1);
                },()-> System.out.println("Project not added"));
            }else {
                System.out.println("Project Add Cancelled");
            }
        } );
    }
    public void calculateTotalCost(Project project,int isRunung){
        Optional<Client> clients = clientController.findById(project.getClient().getId());
        clients.ifPresent(client -> {
            double totalCostMaterial = 0;
            double totalCostLabor = 0;
            double totalCost = 0;
            System.out.println("Client Name : " + client.getName());
            System.out.println("Client Address : " + client.getAddress());
            System.out.println("Client Phone : " + client.getPhone());
            System.out.println("Cost Detail --- ---");
            System.out.println("1. Materials: ");
            List<Material> listMaterial = materialController.getAll(project);
            double vat = (listMaterial.get(0).getVatRate()/100)+1;
            totalCostMaterial= materialController.totalCostMaterial(listMaterial);
            System.out.printf("%-15s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s%n",
                    "Material Name", "Component Type", "VAT Rate", "Unit Cost", "Quantity",
                    "Transport Cost", "Quality Coefficient");
            listMaterial.forEach(material -> {
                System.out.println("____________________________________________________________________________________________________________________________________________________________");
                System.out.printf("%-15s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s\n",
                        material.getName(), material.getComponentType(), material.getVatRate(),
                        material.getUnitCost(), material.getQuantity(), material.getTransportCost(),
                        material.getQualityCoefficient());
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
            });
            System.out.println("**Total cost of materials before VAT: " + totalCostMaterial + " €");
            System.out.println("**Total cost of materials with VAT ("+listMaterial.get(0).getVatRate()+"%): " + totalCostMaterial * vat + " €");


            List<Labor> listLabor = laborController.getAll(project);
            System.out.println("2. Labor: ");
            System.out.printf("%-15s | %-20s | %-20s | %-20s | %-20s | %-20s%n",
                    "Labor Type", "Component Type", "VAT Rate", "Hourly Rate", "Hours Worked",
                    "Productivity Worker");
            totalCostLabor = laborController.totalCostLabor(listLabor);
            listLabor.forEach(labor -> {
                System.out.println("____________________________________________________________________________________________________________________________________________________________");
                System.out.printf("%-15s | %-20s | %-20s | %-20s | %-20s | %-20s\n",
                        labor.getName(), labor.getComponentType(), labor.getVatRate(),
                        labor.getHourlyRate(), labor.getHoursWorked(), labor.getWorkerProductivity()
                       );
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
            });

            System.out.println("**Total labor cost before VAT: " + totalCostLabor + " €");
            System.out.println("**Total labor cost with VAT ("+listMaterial.get(0).getVatRate()+"%): " + totalCostLabor * vat + " €");

            totalCost = (totalCostLabor * vat) + (totalCostMaterial * vat );
            double totalCostTva = totalCost * project.getProfitMargin()/100;
            System.out.println("3. Total cost before margin: " + totalCost+ " €");
            System.out.println("4. Profit margin ("+project.getProfitMargin()+") : "+ totalCostTva + " €");

            System.out.println("**Coût total final du projet : "+ (totalCostTva+totalCost) + " €");
            double totalCostTva2 = totalCostTva + totalCost;

            if(isRunung == 1) {
                projectService.updateProfitCost(project,totalCostTva2 );
                System.out.print("Would you like to add Quote ? (y/n) : ");
                String quote = scanner.nextLine();
                if(quote.equals("y")){
                    System.out.println("Enter the quote issue date (format: dd/mm/yyyy) : ");
                    LocalDate issueDate = LocalDate.parse(scanner.nextLine());
                    System.out.println("Enter the validity date of the quote (format: dd/mm/yyyy) : ");
                    LocalDate validityDate = LocalDate.parse(scanner.nextLine());
                    System.out.println("Would you like to save the quote? (y/n) : ");
                    if(scanner.nextLine().equals("y")){
                        Quote quote1 = new Quote(totalCostTva2,issueDate,validityDate,false,project);
                        Optional<Quote> quote2 = quoteController.save(quote1);
                        quote2.ifPresent(quote3 -> System.out.println("Quote added with success"));
                    }
                }
            }
        });
    }
    public void calculateCost(){
        getAll();
        System.out.print("Enter the project id : ");
        int id = scanner.nextInt();
        Optional<Project> optionalProject = projectService.getById(id);
        optionalProject.ifPresentOrElse(project -> {
            System.out.println(project);
            calculateTotalCost(project,0);
        },()-> System.out.println("Project not found"));
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

    public void getAll(){
        List<Project> projectList = projectService.getAll();
        if(projectList.isEmpty()){
            System.out.println("No project found");
        }else {
            System.out.printf("\n%-15s |%-15s | %-20s | %-20s | %-20s%n","Project Id", "Client Name", "Project Name", "Profit Margin", "Total Cost");
            System.out.println("_____________________________________________________________________________________________");
            projectList.forEach(project -> {
                System.out.printf("%-15d | %-15s | %-20s | %-20s | %-20s\n",project.getId(), project.getClient().getName(), project.getProjectName(), project.getProfitMargin()+" %", project.getTotalCost()+" €");
                System.out.println("-------------------------------------------------------------------------------------------");
            });
        }
    }

}
