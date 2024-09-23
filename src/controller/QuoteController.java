package controller;

import domain.Project;
import domain.Quote;
import service.QuoteService;
import utils.Cl;
import utils.Menu;
import utils.ProjectStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class QuoteController {
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
        boolean valid = false;
        LocalDate issueDate = null;
        do {
            try {
                issueDate = LocalDate.parse(scanner.nextLine());
                valid = true;
            } catch (Exception e) {
                System.out.println(Cl.RED + "Invalid date format" + Cl.RESET);
            }
        }while (!valid);
        System.out.print("Enter the validity date of the quote (format: dd/mm/yyyy) : ");
        LocalDate validityDate = null;
        valid = false;
        do {
            try {
                validityDate = LocalDate.parse(scanner.nextLine());
                valid = true;
            } catch (Exception e) {
                System.out.println(Cl.RED + "Invalid date format" + Cl.RESET);
            }
        }while (!valid);
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
            System.out.print(Cl.CYAN+"Enter the project id : "+ Cl.RESET);
            do {
                if (scanner.hasNextInt()) {
                    break;
                } else {
                    System.out.println(Cl.RED + "Invalid input" + Cl.RESET);
                    scanner.nextLine();
                }
            } while (!scanner.hasNextInt());
            Optional<Project> project =projectController.getById(id); ;
            if(project.isPresent()){
                if(quoteService.getByIdProject(project.get()).isPresent()){
                    System.out.println(Cl.RED+"A quote already exists for this project"+ Cl.RESET);
                    System.out.println(Cl.RED+"For Exit press 0"+ Cl.RESET);
                }else{
                    double totalCostTva = projectController.totalCostProject(project.get());
                    save(project.get(),totalCostTva);
                    exit = true;
                }
            }else{
                System.out.println(Cl.RED+"Project not found"+ Cl.RESET);
                System.out.println(Cl.RED+"For Exit press 0"+ Cl.RESET);
            }
        }while (!exit && id != 0);

    }
    public void getAll(){
        List<Quote> quoteList = quoteService.getAll();

        if (quoteList.isEmpty()) {
            System.out.println(Cl.RED + "No quotes found." + Cl.RESET);
        } else {
            System.out.printf(Cl.CYAN + "\n%-20s | %-20s | %-20s | %-20s | %-20s | %-20s%n" + Cl.RESET,
                    "Project ID ",
                    "Project Name",
                    "Estimated Amount", "Issue Date", "Validity Date", "Accepted");
            System.out.println(Cl.CYAN + "--------------------------------------------------------------------------------------------------------------------------" + Cl.RESET);

            quoteList.forEach(quote -> {
                String accepted = quote.isAccepted() ? Cl.GREEN + "Yes" + Cl.RESET : Cl.RED + "No" + Cl.RESET;
                System.out.printf(Cl.YELLOW+"%-20d "+ Cl.CYAN+"| "+ Cl.YELLOW+"%-20s "+ Cl.CYAN+"|"+ Cl.YELLOW+" %-20s "+ Cl.CYAN+"|"+ Cl.YELLOW+" %-20s "+ Cl.CYAN+"|"+ Cl.YELLOW+" %-20s "+ Cl.CYAN+"|"+ Cl.YELLOW+" %-20s%n"+ Cl.RESET,
                        quote.getProject().getId(),
                        quote.getProject().getProjectName(),
                        quote.getEstimatedAmount() + " €",
                        quote.getIssueDate(),
                        quote.getValidityDate(),
                        accepted);
                System.out.println(Cl.CYAN + "--------------------------------------------------------------------------------------------------------------------------" + Cl.RESET);
            });
        }
    }
    public void displayAllQuote(){
        getAll();
        if(!quoteService.getAll().isEmpty()){
            System.out.print(Cl.CYAN+"Do you want to display the details of a quote? (y/n) : "+ Cl.RESET);
            if(scanner.nextLine().equals("y")){
                System.out.print(Cl.CYAN+"Enter the quote id : "+ Cl.RESET);
                int id = Integer.parseInt(scanner.nextLine());
                ProjectController projectController = new ProjectController();
                Optional<Project> project = projectController.getById(id);
                project.ifPresent(project1 -> {
                    Optional<Quote> quote = quoteService.getByIdProject(project1);
                    if(quote.isPresent()){
                        System.out.println(Cl.RED+"Quote found"+ Cl.RESET);
                        projectController.calculateTotalCost(project1,0);
                    }else{
                        System.out.println(Cl.RED+"Quote not found"+ Cl.RESET);
                    }
                });

            }
        }

    }
    public void updateQuote(){
        getAll();
        System.out.print(Cl.CYAN+"Do you want to update a quote? (y/n) : "+ Cl.RESET);
        if(scanner.nextLine().equals("y")){
            System.out.print(Cl.CYAN+"Enter the project id : "+ Cl.RESET);
            int id = Integer.parseInt(scanner.nextLine());
            ProjectController projectController = new ProjectController();
            Optional<Project> project = projectController.getById(id);
            project.ifPresent(project1 -> {
                Optional<Quote> quote = quoteService.getByIdProject(project1);
                if(quote.isPresent()){
                    System.out.println(Cl.RED+"Quote found"+ Cl.RESET);

                    System.out.print(Cl.CYAN+"Do you want to update the estimated amount ? (y/n) : "+ Cl.RESET);
                    if(scanner.nextLine().equals("y")){
                        System.out.print(Cl.CYAN+"Enter the new estimated amount : "+ Cl.RESET);
                        double estimatedAmount = Double.parseDouble(scanner.nextLine());
                        quote.get().setEstimatedAmount(estimatedAmount);
                    }
                    System.out.print(Cl.CYAN+"Do you want to update the issue date ? (y/n) : "+ Cl.RESET);
                    if(scanner.nextLine().equals("y")){
                        System.out.print(Cl.CYAN+"Enter the new issue date (format: dd/mm/yyyy) : "+ Cl.RESET);
                        LocalDate issueDate = LocalDate.parse(scanner.nextLine());
                        quote.get().setIssueDate(issueDate);
                    }
                    System.out.print(Cl.CYAN+"Do you want to update the validity date ? (y/n) : "+ Cl.RESET);
                    if(scanner.nextLine().equals("y")){
                        System.out.print(Cl.CYAN+"Enter the new validity date (format: dd/mm/yyyy) : "+ Cl.RESET);
                        LocalDate validityDate = LocalDate.parse(scanner.nextLine());
                        quote.get().setValidityDate(validityDate);
                    }
                    System.out.print(Cl.CYAN+"Do you want to update the acceptance status ? (y/n) : "+ Cl.RESET);
                    if(scanner.nextLine().equals("y")){
                        System.out.print(Cl.CYAN+"Enter the new acceptance status (true/false) : "+ Cl.RESET);
                        boolean accepted = Boolean.parseBoolean(scanner.nextLine());
                        if(accepted){
                            if(!quote.get().getValidityDate().isAfter(LocalDate.now())){
                                System.out.println(Cl.RED+"The validity date is expired"+ Cl.RESET);
                                accepted = false;
                            }else {
                                System.out.println(Cl.GREEN+"The validity date is not expired"+ Cl.RESET);
                            }
                        }
                        quote.get().setAccepted(accepted);
                    }
                    System.out.print(Cl.CYAN+"Would you like to update the quote? (y/n) : "+ Cl.RESET);
                    if(scanner.nextLine().equals("y")){
                        Optional<Quote> optionalQuote= quoteService.update(quote.get());
                        optionalQuote.ifPresent(quote1 ->
                                System.out.println(Cl.GREEN+"Quote updated with success"+ Cl.RESET));
                                if (quote.get().isAccepted()) {
                                    project1.setProjectStatus(ProjectStatus.IN_PROGRESS);
                                    projectController.update(project1);
                                }else {
                                    project1.setProjectStatus(ProjectStatus.CANCELED);
                                    projectController.update(project1);
                                }
                    }else System.out.println(Cl.RED+"Quote not updated"+ Cl.RESET);
                }else{
                    System.out.println(Cl.RED+"Quote not found"+ Cl.RESET);
                }
            });
        }
    }

    public void deleteQuote(){
        getAll();
        System.out.print(Cl.CYAN+"Do you want to delete a quote? (y/n) : "+ Cl.RESET);
        if(scanner.nextLine().equals("y")){
            System.out.print(Cl.CYAN+"Enter the project id : "+ Cl.RESET);
            int id = Integer.parseInt(scanner.nextLine());
            ProjectController projectController = new ProjectController();
            Optional<Project> project = projectController.getById(id);
            project.ifPresent(project1 -> {
                Optional<Quote> quote = quoteService.getByIdProject(project1);
                if(quote.isPresent()){
                    System.out.println(Cl.RED+"Quote found"+ Cl.RESET);
                    System.out.print(Cl.CYAN+"Would you like to delete the quote? (y/n) : "+ Cl.RESET);
                    if(scanner.nextLine().equals("y")){
                        quoteService.delete(quote.get());
                        project1.setProjectStatus(ProjectStatus.CANCELED);
                        projectController.update(project1);
                        System.out.println(Cl.GREEN+"Quote deleted with success"+ Cl.RESET);
                    }else System.out.println(Cl.RED+"Quote not deleted"+ Cl.RESET);
                }else{
                    System.out.println(Cl.RED+"Quote not found"+ Cl.RESET);
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
