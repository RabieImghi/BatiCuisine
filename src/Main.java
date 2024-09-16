import controller.ClientController;
import controller.ProjectController;
import repository.ClientRepository;
import utils.Menu;

import java.util.Scanner;

public class Main {
    static Menu menu = new Menu();
    static Scanner scanner = new Scanner(System.in);
    static ClientController clientController = new ClientController();
    static ProjectController projectController = new ProjectController();
    public static void main(String[] args) {
        boolean exit = false;
        do{
            menu.showMenu();
            String option = scanner.nextLine();

            switch (option){
                case "1" : clientController.save();break;
                case "2" : clientController.getAll();break;
                case "3" : clientController.getByName(); break;
                case "4" : clientController.getById(); break;
                case "5" : clientController.update();
                case "6" : clientController.delete();
                case "8" : projectController.save();break;
                default:
                    System.out.println("inc");
            }



        }while (!exit);
    }
}