package controller;

import domain.Client;
import service.ClientService;

import java.util.Scanner;

public class ClientController {
    public Scanner scanner = new Scanner(System.in);
    private ClientService clientService = new ClientService();
    public void createClient() {
        String name;
        String address;
        String phone;
        boolean isProfessional;
        System.out.println("Enter the name of the client: ");
        name = scanner.nextLine();
        System.out.println("Enter the address of the client: ");
        address = scanner.nextLine();
        System.out.println("Enter the phone number of the client: ");
        phone = scanner.nextLine();
        System.out.println("Is the client a professional? (yes/no)");
        isProfessional = scanner.nextLine().equals("yes");
        Client client = new Client(name, address, phone, isProfessional);
        clientService.save(client);

    }
}
