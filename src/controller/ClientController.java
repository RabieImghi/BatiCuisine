package controller;

import domain.Client;
import service.ClientService;
import utils.Cl;

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
        System.out.println(Cl.CYAN + "Enter the information of the client: " + Cl.RESET);
        do {
            System.out.print(Cl.YELLOW + "Enter the name of the client: " + Cl.RESET);
            name = scanner.nextLine();
            optionalClient = clientService.getByName(name);
            if (optionalClient.isPresent()) {
                System.out.println(Cl.RED + "This client name already exists!" + Cl.RESET);
            }
        } while (optionalClient.isPresent());
        System.out.print(Cl.YELLOW + "Enter the address of the client: " + Cl.RESET);
        address = scanner.nextLine();
        System.out.print(Cl.YELLOW + "Enter the phone number of the client: " + Cl.RESET);
        phone = scanner.nextLine();
        System.out.print(Cl.YELLOW + "Is the client a professional? (y/no) : " + Cl.RESET);
        isProfessional = scanner.nextLine().equalsIgnoreCase("y");
        Client client = new Client(name, address, phone, isProfessional);
        optionalClient = clientService.save(client);
        optionalClient.ifPresentOrElse(client1 -> {
            System.out.println(Cl.GREEN + "Client added successfully!" + Cl.RESET);
        }, () -> {
            System.out.println(Cl.RED + "Client not added." + Cl.RESET);
        });
        return optionalClient;
    }
    public void getAll(){
        List<Client> clientList = clientService.getAll();
        System.out.println(Cl.CYAN + "\n============================== Client List ==============================" + Cl.RESET);
        System.out.printf(Cl.BLUE + "%-15s | %-20s | %-20s | %-20s | %-20s%n" + Cl.RESET,"Client Id", "Client Name", "Client Address", "Client Phone", "Is Professional");
        System.out.println(Cl.YELLOW + "_________________________________________________________________________________" + Cl.RESET);
        clientList.forEach(client -> {
            String isProfessional = client.isProfessional() ? Cl.GREEN + "Professional" + Cl.RESET : Cl.RED
                    + "Not Professional" + Cl.RESET;
            System.out.printf("%-15d | %-20s | %-20s | %-20s | %-20s\n",client.getId(),client.getName(), client.getAddress(), client.getPhone(), isProfessional);
            System.out.println(Cl.YELLOW + "-------------------------------------------------------------------------------" + Cl.RESET);
        });
        if (clientList.isEmpty()) {
            System.out.println(Cl.RED + "No clients found." + Cl. RESET);
        }
    }
    public Optional<Client> findById(int id){
        return clientService.getById(id);
    }

    public Optional<Client> getClient(){
        Optional<Client> client = Optional.empty();
        String searchOption;
        do{
            System.out.println(Cl.BLUE + "1 : Search Client By Id" + Cl.RESET);
            System.out.println(Cl.BLUE + "2 : Search Client By Name" + Cl.RESET);
            System.out.println(Cl.BLUE + "3 : Back <- " + Cl.RESET);
            System.out.print(Cl.YELLOW + "Choose an option: " + Cl.RESET);
            searchOption = scanner.nextLine();
            switch (searchOption) {
                case "1": {
                    getAll();
                    System.out.print(Cl.YELLOW + "Client Id: " + Cl.RESET);
                    String idClient = scanner.nextLine();
                    try {
                        client = clientService.getById(Integer.parseInt(idClient));
                    } catch (NumberFormatException e) {
                        System.out.println(Cl.RED + "Invalid ID format. Please enter a valid number." + Cl.RESET);
                    }
                    break;
                }
                case "2": {
                    getAll();
                    System.out.print(Cl.YELLOW + "Client Name: " + Cl.RESET);
                    String nameClient = scanner.nextLine();
                    client = clientService.getByName(nameClient);
                    break;
                }
                default: {
                    System.out.println(Cl.RED + "Invalid option. Please choose 1 or 2." + Cl.RESET);
                    break;
                }
            }
        }while (!searchOption.equals("3") && client.isEmpty());
        return client;
    }
    public void update(){
        Optional<Client> client = getClient();
        client.ifPresentOrElse(client1 -> {
            System.out.println(Cl.CYAN + "Client Info: " + client1.toString() + Cl.RESET);
            System.out.println(Cl.YELLOW + "Enter the new information of the client: \n" + Cl.RESET);
            System.out.print(Cl.YELLOW + "New address of the client: " + Cl.RESET);
            client1.setAddress(scanner.nextLine());
            System.out.print(Cl.YELLOW + "New phone number of the client: " + Cl.RESET);
            client1.setPhone(scanner.nextLine());
            Optional<Client> clientOptional = clientService.update(client1);
            clientOptional.ifPresentOrElse(client2 -> {
                System.out.println(Cl.GREEN + "Client updated successfully!" + Cl.RESET);
            }, () -> {
                System.out.println(Cl.RED + "Client not updated." + Cl.RESET);
            });

        }, () -> {
            System.out.println(Cl.RED + "Client not found." + Cl.RESET);
        });
    }
    public void delete(){
        Optional<Client> client = getClient();
        client.ifPresentOrElse(client1 -> {
            System.out.println(Cl.CYAN + "Client Info: " + client1.toString() + Cl.RESET);
            System.out.println(Cl.YELLOW + "Are you sure you want to delete this client? (y/n)" + Cl.RESET);
            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                System.out.println(Cl.RED + "Client Not Deleted" + Cl.RESET);
            } else {
                Optional<Client> clientOptional = clientService.delete(client1);
                clientOptional.ifPresentOrElse(
                        client2 -> System.out.println(Cl.GREEN + "Client deleted successfully!" + Cl.RESET),
                        () -> System.out.println(Cl.RED + "Client Not Deleted" + Cl.RESET)
                );
            }
        }, () -> System.out.println(Cl.RED + "Client Not Found" + Cl.RESET));
    }
}
