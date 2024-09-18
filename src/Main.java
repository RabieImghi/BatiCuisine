import controller.*;
import repository.ClientRepository;
import utils.Menu;

import java.util.Scanner;

public class Main {
    static Menu menu = new Menu();
    static Scanner scanner = new Scanner(System.in);
    static ClientController clientController = new ClientController();
    static LaborController laborController = new LaborController();
    static MaterialController materialController = new MaterialController();
    static ProjectController projectController = new ProjectController();
    static QuoteController quoteController = new QuoteController();
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
                case "6": projectController.getAll(); break;
                case "7": quoteController.addQuoteMenu(); break;
                case "8": materialController.saveNewMaterial(); break;
                case "9": laborController.saveNewLabor(); break;
                case "10": exit = true; break;
                default:
                    System.out.println("Chois Incorect");
            }
        }while (!exit);
    }
}