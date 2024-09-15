import controller.ClientController;
import repository.ClientRepository;
import utils.Menu;

import java.util.Scanner;

public class Main {
    static Menu menu = new Menu();
    static Scanner scanner = new Scanner(System.in);
    static ClientController clientController = new ClientController();
    public static void main(String[] args) {
        boolean exit = false;
        do{
            menu.showMenu();
            String option = scanner.nextLine();

            switch (option){
                case "1" : clientController.createClient();break;
                case "2" : clientController.showClients();break;
                default:
                    System.out.println("inc");
            }



        }while (!exit);
    }
}