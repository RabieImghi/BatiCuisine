package controller;

import domain.Client;
import service.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientController {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    private final Scanner scanner = new Scanner(System.in);
    private final ClientService clientService = new ClientService();

    public Optional<Client> save() {
        String name;
        String address;
        String phone;
        boolean isProfessional;
        Optional<Client> optionalClient;
        System.out.println(CYAN + "Enter the information of the client: " + RESET);
        do {
            System.out.print(YELLOW + "Enter the name of the client: " + RESET);
            name = scanner.nextLine();
            optionalClient = clientService.getByName(name);
            if (optionalClient.isPresent()) {
                System.out.println(RED + "This client name already exists!" + RESET);
            }
        } while (optionalClient.isPresent());
        System.out.print(YELLOW + "Enter the address of the client: " + RESET);
        address = scanner.nextLine();
        System.out.print(YELLOW + "Enter the phone number of the client: " + RESET);
        phone = scanner.nextLine();
        System.out.print(YELLOW + "Is the client a professional? (y/no) : " + RESET);
        isProfessional = scanner.nextLine().equalsIgnoreCase("y");
        Client client = new Client(name, address, phone, isProfessional);
        optionalClient = clientService.save(client);
        optionalClient.ifPresentOrElse(client1 -> {
            System.out.println(GREEN + "Client added successfully!" + RESET);
        }, () -> {
            System.out.println(RED + "Client not added." + RESET);
        });
        return optionalClient;
    }
    public void getAll(){
        List<Client> clientList = clientService.getAll();
        System.out.println(CYAN + "\n============================== Client List ==============================" + RESET);
        System.out.printf(BLUE + "%-15s | %-20s | %-20s | %-20s%n" + RESET, "Client Name", "Client Address", "Client Phone", "Is Professional");
        System.out.println(YELLOW + "_________________________________________________________________________________" + RESET);
        clientList.forEach(client -> {
            String isProfessional = client.isProfessional() ? GREEN + "Professional" + RESET : RED + "Not Professional" + RESET;
            System.out.printf("%-15s | %-20s | %-20s | %-20s\n", client.getName(), client.getAddress(), client.getPhone(), isProfessional);
            System.out.println(YELLOW + "-------------------------------------------------------------------------------" + RESET);
        });
        if (clientList.isEmpty()) {
            System.out.println(RED + "No clients found." + RESET);
        }
    }
    public Optional<Client> findById(int id){
        return clientService.getById(id);
    }

    public Optional<Client> getClient(){
        Optional<Client> client = Optional.empty();
        System.out.println(BLUE + "1 : Search Client By Id" + RESET);
        System.out.println(BLUE + "2 : Search Client By Name" + RESET);
        System.out.print(YELLOW + "Choose an option: " + RESET);
        switch (scanner.nextLine()) {
            case "1": {
                getAll();
                System.out.print(YELLOW + "Client Id: " + RESET);
                String idClient = scanner.nextLine();
                try {
                    client = clientService.getById(Integer.parseInt(idClient));
                } catch (NumberFormatException e) {
                    System.out.println(RED + "Invalid ID format. Please enter a valid number." + RESET);
                }
                break;
            }
            case "2": {
                getAll();
                System.out.print(YELLOW + "Client Name: " + RESET);
                String nameClient = scanner.nextLine();
                client = clientService.getByName(nameClient);
                break;
            }
            default: {
                System.out.println(RED + "Invalid option. Please choose 1 or 2." + RESET);
                break;
            }
        }
        return client;
    }
    public void update(){
        Optional<Client> client = getClient();
        client.ifPresentOrElse(client1 -> {
            System.out.println(CYAN + "Client Info: " + client1.toString() + RESET);
            System.out.println(YELLOW + "Enter the new information of the client: \n" + RESET);
            System.out.print(YELLOW + "New address of the client: " + RESET);
            client1.setAddress(scanner.nextLine());
            System.out.print(YELLOW + "New phone number of the client: " + RESET);
            client1.setPhone(scanner.nextLine());
            Optional<Client> clientOptional = clientService.update(client1);
            clientOptional.ifPresentOrElse(client2 -> {
                System.out.println(GREEN + "Client updated successfully!" + RESET);
            }, () -> {
                System.out.println(RED + "Client not updated." + RESET);
            });

        }, () -> {
            System.out.println(RED + "Client not found." + RESET);
        });
    }
    public void delete(){
        Optional<Client> client = getClient();
        client.ifPresentOrElse(client1 -> {
            System.out.println(CYAN + "Client Info: " + client1.toString() + RESET);
            System.out.println(YELLOW + "Are you sure you want to delete this client? (y/n)" + RESET);
            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                System.out.println(RED + "Client Not Deleted" + RESET);
            } else {
                Optional<Client> clientOptional = clientService.delete(client1);
                clientOptional.ifPresentOrElse(
                        client2 -> System.out.println(GREEN + "Client deleted successfully!" + RESET),
                        () -> System.out.println(RED + "Client Not Deleted" + RESET)
                );
            }
        }, () -> System.out.println(RED + "Client Not Found" + RESET));
    }
}
