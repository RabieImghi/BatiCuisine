package controller;

import domain.Client;
import service.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientController {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientService clientService = new ClientService();
    public Optional<Client> save() {
        String name;
        String address;
        String phone;
        boolean isProfessional;
        Optional<Client> optionalClient;
        System.out.println("Enter the information of the client : \n");
        do {
            System.out.print("Enter the name of the client: ");
            name = scanner.nextLine();
            optionalClient=clientService.getByName(name);
            if(optionalClient.isPresent()){
                System.out.println("This client name already exists");
            }
        }while (optionalClient.isPresent());
        System.out.print("Enter the address of the client: ");
        address = scanner.nextLine();
        System.out.print("Enter the phone number of the client: ");
        phone = scanner.nextLine();
        System.out.println("Is the client a professional? (yes/no)");
        isProfessional = scanner.nextLine().equals("yes");
        Client client = new Client(name, address, phone, isProfessional);
        optionalClient= clientService.save(client);
        optionalClient.ifPresentOrElse(client1 -> {
            System.out.println("Client added successfully");
        },()-> System.out.println("Client not added"));
        return optionalClient;
    }
    public void getAll(){
        List<Client> clientList= clientService.getAll();
        System.out.printf("\n%-15s | %-20s | %-20s | %-20s%n", "Client Name", "Client Address", "Client Phone", "Is Professional");
        System.out.println("_________________________________________________________________________________");
        clientList.forEach(client -> {
            String isProfessional = client.isProfessional() ? "Professional" : "Not Professional";
            System.out.printf("%-15s | %-20s | %-20s | %-20s\n", client.getName(), client.getAddress(), client.getPhone(), isProfessional);
            System.out.println("-------------------------------------------------------------------------------");
        });
    }
    public Optional<Client> findById(int id){
        return clientService.getById(id);
    }

    public Optional<Client> getClient(){
        Optional<Client> client = Optional.empty();
        System.out.println("1 : Search Client By Id");
        System.out.println("2 : Search Client By Name");
        System.out.print("Choose an option : ");
        switch (scanner.nextLine()){
            case "1" : {
                getAll();
                System.out.print("Client Id : ");
                String idClient = scanner.nextLine();
                client = clientService.getById(Integer.parseInt(idClient));
                break;
            }
            case "2" :{
                getAll();
                System.out.print("Client Name : ");
                String nameClient = scanner.nextLine();
                client = clientService.getByName(nameClient);
                break;
            }
            default:
                System.out.println("Invalid option");
        }
        return client;
    }
    public void update(){
        Optional<Client> client = getClient();
        client.ifPresentOrElse(client1 -> {
            System.out.println("Client Info : "+client1.toString());
            System.out.println("Enter the new information of the client : \n");
            System.out.print("New address of the client: ");
            client1.setAddress(scanner.nextLine());
            System.out.print("New  phone number of the client: ");
            client1.setPhone(scanner.nextLine());
            Optional<Client> clientOptional= clientService.update(client1);
            clientOptional.ifPresentOrElse(client2 -> {
                System.out.println("Client Updated with success");
                }, ()-> System.out.println("Client not updated"));
        },()-> System.out.println("Client Not found"));
    }
    public void delete(){
        Optional<Client> client= getClient();
        client.ifPresentOrElse(client1 -> {
            System.out.println("Client Info : "+client1.toString());
            System.out.println("Are you sure you want to delete this client? (yes/no)");
            if(!scanner.nextLine().equals("yes")){
                System.out.println("Client Not Deleted");
            }else {
                Optional<Client> clientOptional = clientService.delete(client1);
                clientOptional.ifPresentOrElse(
                        client2 -> System.out.println("Client deleted with success"),
                        ()-> System.out.println("Client Not Deleted"));
            }
        },()-> System.out.println("Client Not Found"));
    }
}
