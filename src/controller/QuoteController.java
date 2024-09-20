package controller;

import domain.Project;
import domain.Quote;
import service.QuoteService;
import utils.Menu;
import utils.ProjectStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class QuoteController {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    private final QuoteService quoteService = new QuoteService();
    private final Scanner scanner = new Scanner(System.in);
    public void manageQuote(){
        boolean exit = false;
        do{
            Menu.showQuoteMenu();
            String choice = scanner.nextLine();
            switch (choice){
                case "1":
                    displayAllQuote();
                    break;
                case "2":
                    addQuoteMenu();
                    break;
                case "3":
                    updateQuote();
                    break;
                case "4":
                    deleteQuote();
                    break;
                case "5":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }while (!exit);
    }
    public void save(Project project, double totalCostTva){
        System.out.print("Enter the quote issue date (format: dd/mm/yyyy) : ");
        LocalDate issueDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter the validity date of the quote (format: dd/mm/yyyy) : ");
        LocalDate validityDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Would you like to save the quote? (y/n) : ");
        if(scanner.nextLine().equals("y")){
            Quote quote1 = new Quote(totalCostTva,issueDate,validityDate,false,project);
            Optional<Quote> quote2 = quoteService.save(quote1);
            quote2.ifPresent(quote3 -> System.out.println("Quote added with success"));
        }
    }
    public void addQuoteMenu(){
        ProjectController projectController = new ProjectController();
        projectController.getAll();
        boolean exit = false;
        int id = 0;
        do {
            System.out.print(CYAN+"Enter the project id : "+RESET);
            id = Integer.parseInt(scanner.nextLine());
            Optional<Project> project =projectController.getById(id); ;
            if(project.isPresent()){
                if(quoteService.getByIdProject(project.get()).isPresent()){
                    System.out.println(RED+"A quote already exists for this project"+RESET);
                    System.out.println(RED+"For Exit press 0"+RESET);
                }else{
                    double totalCostTva = projectController.totalCostProject(project.get());
                    save(project.get(),totalCostTva);
                    exit = true;
                }
            }else{
                System.out.println(RED+"Project not found"+RESET);
                System.out.println(RED+"For Exit press 0"+RESET);
            }
        }while (!exit && id != 0);

    }
    public void getAll(){
        List<Quote> quoteList = quoteService.getAll();

        if (quoteList.isEmpty()) {
            System.out.println(RED + "No quotes found." + RESET);
        } else {
            System.out.printf(CYAN + "\n%-20s | %-20s | %-20s | %-20s | %-20s | %-20s%n" + RESET,
                    "Project ID ",
                    "Project Name",
                    "Estimated Amount", "Issue Date", "Validity Date", "Accepted");
            System.out.println(CYAN + "--------------------------------------------------------------------------------------------------------------------------" + RESET);

            quoteList.forEach(quote -> {
                String accepted = quote.isAccepted() ? GREEN + "Yes" + RESET : RED + "No" + RESET;
                System.out.printf(YELLOW+"%-20d "+CYAN+"| "+YELLOW+"%-20s "+CYAN+"|"+YELLOW+" %-20s "+CYAN+"|"+YELLOW+" %-20s "+CYAN+"|"+YELLOW+" %-20s "+CYAN+"|"+YELLOW+" %-20s%n"+RESET,
                        quote.getProject().getId(),
                        quote.getProject().getProjectName(),
                        quote.getEstimatedAmount() + " €",
                        quote.getIssueDate(),
                        quote.getValidityDate(),
                        accepted);
                System.out.println(CYAN + "--------------------------------------------------------------------------------------------------------------------------" + RESET);
            });
        }
    }
    public void displayAllQuote(){
        getAll();
        if(!quoteService.getAll().isEmpty()){
            System.out.print(CYAN+"Do you want to display the details of a quote? (y/n) : "+RESET);
            if(scanner.nextLine().equals("y")){
                System.out.print(CYAN+"Enter the quote id : "+RESET);
                int id = Integer.parseInt(scanner.nextLine());
                ProjectController projectController = new ProjectController();
                Optional<Project> project = projectController.getById(id);
                project.ifPresent(project1 -> {
                    Optional<Quote> quote = quoteService.getByIdProject(project1);
                    if(quote.isPresent()){
                        System.out.println(RED+"Quote found"+RESET);
                        projectController.calculateTotalCost(project1,0);
                    }else{
                        System.out.println(RED+"Quote not found"+RESET);
                    }
                });

            }
        }

    }
    public void updateQuote(){
        getAll();
        System.out.print(CYAN+"Do you want to update a quote? (y/n) : "+RESET);
        if(scanner.nextLine().equals("y")){
            System.out.print(CYAN+"Enter the project id : "+RESET);
            int id = Integer.parseInt(scanner.nextLine());
            ProjectController projectController = new ProjectController();
            Optional<Project> project = projectController.getById(id);
            project.ifPresent(project1 -> {
                Optional<Quote> quote = quoteService.getByIdProject(project1);
                if(quote.isPresent()){
                    System.out.println(RED+"Quote found"+RESET);

                    System.out.print(CYAN+"Do you want to update the estimated amount ? (y/n) : "+RESET);
                    if(scanner.nextLine().equals("y")){
                        System.out.print(CYAN+"Enter the new estimated amount : "+RESET);
                        double estimatedAmount = Double.parseDouble(scanner.nextLine());
                        quote.get().setEstimatedAmount(estimatedAmount);
                    }
                    System.out.print(CYAN+"Do you want to update the issue date ? (y/n) : "+RESET);
                    if(scanner.nextLine().equals("y")){
                        System.out.print(CYAN+"Enter the new issue date (format: dd/mm/yyyy) : "+RESET);
                        LocalDate issueDate = LocalDate.parse(scanner.nextLine());
                        quote.get().setIssueDate(issueDate);
                    }
                    System.out.print(CYAN+"Do you want to update the validity date ? (y/n) : "+RESET);
                    if(scanner.nextLine().equals("y")){
                        System.out.print(CYAN+"Enter the new validity date (format: dd/mm/yyyy) : "+RESET);
                        LocalDate validityDate = LocalDate.parse(scanner.nextLine());
                        quote.get().setValidityDate(validityDate);
                    }
                    System.out.print(CYAN+"Do you want to update the acceptance status ? (y/n) : "+RESET);
                    if(scanner.nextLine().equals("y")){
                        System.out.print(CYAN+"Enter the new acceptance status (true/false) : "+RESET);
                        boolean accepted = Boolean.parseBoolean(scanner.nextLine());
                        if(accepted){
                            if(!quote.get().getValidityDate().isAfter(LocalDate.now())){
                                System.out.println(RED+"The validity date is expired"+RESET);
                                accepted = false;
                            }else {
                                System.out.println(GREEN+"The validity date is not expired"+RESET);
                            }
                        }
                        quote.get().setAccepted(accepted);
                    }
                    System.out.print(CYAN+"Would you like to update the quote? (y/n) : "+RESET);
                    if(scanner.nextLine().equals("y")){
                        Optional<Quote> optionalQuote= quoteService.update(quote.get());
                        optionalQuote.ifPresent(quote1 ->
                                System.out.println(GREEN+"Quote updated with success"+RESET));
                                if (quote.get().isAccepted()) {
                                    project1.setProjectStatus(ProjectStatus.IN_PROGRESS);
                                    projectController.update(project1);
                                }else {
                                    project1.setProjectStatus(ProjectStatus.CANCELED);
                                    projectController.update(project1);
                                }
                    }else System.out.println(RED+"Quote not updated"+RESET);
                }else{
                    System.out.println(RED+"Quote not found"+RESET);
                }
            });
        }
    }

    public void deleteQuote(){
        getAll();
        System.out.print(CYAN+"Do you want to delete a quote? (y/n) : "+RESET);
        if(scanner.nextLine().equals("y")){
            System.out.print(CYAN+"Enter the project id : "+RESET);
            int id = Integer.parseInt(scanner.nextLine());
            ProjectController projectController = new ProjectController();
            Optional<Project> project = projectController.getById(id);
            project.ifPresent(project1 -> {
                Optional<Quote> quote = quoteService.getByIdProject(project1);
                if(quote.isPresent()){
                    System.out.println(RED+"Quote found"+RESET);
                    System.out.print(CYAN+"Would you like to delete the quote? (y/n) : "+RESET);
                    if(scanner.nextLine().equals("y")){
                        quoteService.delete(quote.get());
                        project1.setProjectStatus(ProjectStatus.CANCELED);
                        projectController.update(project1);
                        System.out.println(GREEN+"Quote deleted with success"+RESET);
                    }else System.out.println(RED+"Quote not deleted"+RESET);
                }else{
                    System.out.println(RED+"Quote not found"+RESET);
                }
            });
        }
    }
    public void amountEstimateUpdate(Project project){
        Optional<Quote> quote = quoteService.getByIdProject(project);
        if(quote.isPresent()){
            quote.get().setEstimatedAmount(project.getTotalCost());
            quoteService.update(quote.get());
        }
    }


}
