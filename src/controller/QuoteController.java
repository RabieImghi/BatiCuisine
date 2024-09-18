package controller;

import domain.Project;
import domain.Quote;
import service.QuoteService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class QuoteController {
    private final QuoteService quoteService = new QuoteService();
    private final Scanner scanner = new Scanner(System.in);
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
        System.out.print("Enter the project id : ");
        int id = Integer.parseInt(scanner.nextLine());
        Optional<Project> project =projectController.getById(id); ;
        if(project.isPresent()){
            double totalCostTva = projectController.totalCostProject(project.get());
            save(project.get(),totalCostTva);
        }else{
            System.out.println("Project not found");
        }
    }

}
