package controller;

import domain.Client;
import domain.Project;
import service.ClientService;
import service.ProjectService;

import java.util.Optional;
import java.util.Scanner;

public class ProjectController {

    private final ProjectService projectService = new ProjectService();
    private final ClientService clientService = new ClientService();
    private final ClientController clientController = new ClientController();
    private final Scanner scanner = new Scanner(System.in);
    public void save() {
        Optional<Client> clients = getClient();
        clients.ifPresent(client ->{
            System.out.println("Project Name");
            String name = scanner.nextLine();
            System.out.println("Surface Cuisine");
            double surfaceCuisine = scanner.nextDouble();

            Project project = new Project(name, client);
            projectService.save(project);
        } );




    }
    public Optional<Client> getClient() {
        System.out.println("1 : Search Client ");
        System.out.println("2 : Add Client ");
        String option = scanner.nextLine();
        switch (option){
            case "1" : {
                System.out.println("Give Me Name Of Client");
                String name = scanner.nextLine();
                return clientService.getByName(name);
            }
            case "2" :{
                return clientController.save();
            }
            default:
                System.out.println("Invalid option");
                return Optional.empty();
        }

    }

}
