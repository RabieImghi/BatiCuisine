package controller;

import domain.Client;
import domain.Labor;
import domain.Material;
import domain.Project;
import service.ClientService;
import service.ProjectService;

import java.util.Optional;
import java.util.Scanner;

public class ProjectController {

    private final ProjectService projectService = new ProjectService();
    private final ClientService clientService = new ClientService();
    private final ClientController clientController = new ClientController();
    private final MaterialController materialController = new MaterialController();
    private final LaborController laborController = new LaborController();

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
                System.out.println("Surface Cuisine");
                double surfaceCuisine = scanner.nextDouble();
                Project project = new Project(name, client);
                projectService.save(project).ifPresentOrElse(project1 -> {
                    System.out.println("Project added successfully");
                    saveMaterial(project1);
                    saveLabor(project1);
                },()-> System.out.println("Project not added"));
            }else {
                System.out.println("Project Add Cancelled");
            }
        } );
    }
    public void saveMaterial(Project project){
        Optional<Material> optionalMaterial ;
        do{
            optionalMaterial =  materialController.save(project);
        }while (optionalMaterial.isEmpty());
    }
    public void saveLabor(Project project){
        Optional<Labor> optionalLabor;
        do{
            optionalLabor =  laborController.save(project);
        }while (optionalLabor.isEmpty());
    }
    public Optional<Client> getClient() {
        System.out.println("1 : Search Client ");
        System.out.println("2 : Add Client ");
        String option;
        do {
            option = scanner.nextLine();
            switch (option) {
                case "1": {
                    System.out.println("Give Me Name Of Client");
                    String name = scanner.nextLine();
                    return clientService.getByName(name);
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


}
