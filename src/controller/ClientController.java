package controller;

import domain.Client;
import service.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientController {
    public Scanner scanner = new Scanner(System.in);
    private final ClientService clientService = new ClientService();
    public Optional<Client> save() {
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
        Optional<Client> optionalClient= clientService.save(client);
        optionalClient.ifPresentOrElse(System.out::println,()-> System.out.println("not added"));
        return optionalClient;
    }
    public void getAll(){
        List<Client> clientList= clientService.getAll();
        clientList.forEach(System.out::println);
    }
    public void  getByName(){
        System.out.println("name : ");
        String name = scanner.nextLine();
        Optional<Client> client= clientService.getByName(name);
        client.ifPresentOrElse(System.out::println, ()-> System.out.println("Not exist"));
    }
    public void getById(){
        System.out.println("Id : ");
        int id = scanner.nextInt();
        Optional<Client> client= clientService.getById(id);
        client.ifPresentOrElse(System.out::println, ()-> System.out.println("Not exist"));
    }
    public void update(){
        System.out.println("Id : ");
        int id = scanner.nextInt();
        String defultEntre = scanner.nextLine();
        Optional<Client> client= clientService.getById(id);
        client.ifPresentOrElse(client1 -> {
            System.out.println("Enter the name of the client: ");
            client1.setName(scanner.nextLine());
            System.out.println("Enter the address of the client: ");
            client1.setAddress(scanner.nextLine());
            System.out.println("Enter the phone number of the client: ");
            client1.setPhone(scanner.nextLine());
            Optional<Client> clientOptional= clientService.update(client1);
            clientOptional.ifPresentOrElse(System.out::println,()-> System.out.println("not update"));
        },()-> System.out.println("not exi"));
    }
    public void delete(){
        System.out.println("Id : ");
        int id = scanner.nextInt();
        Optional<Client> client= clientService.getById(id);
        client.ifPresentOrElse(client1 -> {
            Optional<Client> clientOptional = clientService.delete(client1);
            clientOptional.ifPresentOrElse(System.out::println,()-> System.out.println("Not delete"));
        },()-> System.out.println("not found"));
    }
}
