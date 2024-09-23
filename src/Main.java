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

        boolean exit = false;
        do{
            menu.showMenu();
            String option = scanner.nextLine();

            switch (option){
                case "1": clientController.save(); break;
                case "2": clientController.getAll(); break;
                case "3": clientController.update(); break;
                case "4": clientController.delete(); break;
                case "5": projectController.manageProject(); break;
                case "6": projectController.getProjectDetail(); break;
                case "7": quoteController.manageQuote(); break;
                case "8": materialController.manageMaterial(); break;
                case "9": laborController.manageLabor(); break;
                case "10": exit = true; break;
                default:
                    System.out.println(RED+"Invalid choice !"+RESET);
            }
        }while (!exit);
    }
}