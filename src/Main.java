import controller.*;
import repository.ClientRepository;
import utils.Menu;

import java.lang.reflect.Array;
import java.util.*;

public class Main {
    static Menu menu = new Menu();
    static Scanner scanner = new Scanner(System.in);
    static ClientController clientController = new ClientController();
    static LaborController laborController = new LaborController();
    static MaterialController materialController = new MaterialController();
    static ProjectController projectController = new ProjectController();
    static QuoteController quoteController = new QuoteController();
    static String RED = "\u001B[31m";
    static String RESET = "\u001B[0m";
    public static void main(String[] args) {
        String option;
        do{
            menu.showMenu();
            option = scanner.nextLine();

            switch (option){
                case "1" : clientController.manageClient(); break;
                case "2": projectController.manageProject(); break;
                case "3": projectController.getProjectDetail(); break;
                case "4": quoteController.manageQuote(); break;
                case "5": materialController.manageMaterial(); break;
                case "6": laborController.manageLabor(); break;
                case "7": break;
                default:
                    System.out.print(RED+"Invalid choice ! 1-7 : "+RESET);
            }
        }while (!option.equals("7"));
    }
}