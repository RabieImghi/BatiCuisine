package controller;

import domain.Labor;
import domain.Material;
import domain.Project;
import service.LaborService;
import utils.ComponentType;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LaborController {
    private final LaborService laborService = new LaborService();
    private final Scanner scanner = new Scanner(System.in);

    public Optional<Labor> save(Project project){
        System.out.print("Do you want to add a Labor? (yes/no) : ");
        if(scanner.nextLine().equals("yes")){
            System.out.print("Enter the labor type (e.g., Basic Worker, Specialist): ");
            String name = scanner.nextLine();
            System.out.print("Enter the hourly rate for this labor (€/h): ");
            double hourlyRate = scanner.nextDouble();
            System.out.print("Enter the number of hours worked: ");
            double hoursWorked = scanner.nextDouble();
            System.out.print("Enter the productivity factor (1.0 = standard, > 1.0 = high productivity): ");
            double workerProductivity = scanner.nextDouble();
            String def = scanner.nextLine();
            Labor labor = new Labor(name,String.valueOf(ComponentType.LABOR),0,hourlyRate,hoursWorked,workerProductivity,project);
            laborService.save(labor).ifPresentOrElse(labor1 -> {
                System.out.println("Labor added successfully");
            },()-> System.out.println("Labor not added"));
            return Optional.of(labor);
        }else {
            System.out.println("Labor Add Cancelled");
            return Optional.empty();
        }

    }
    public List<Labor> getAll(Project project){
        return laborService.getAll(project);
    }
    public double totalCostLabor(List<Labor> listMaterial){
        return laborService.totalCostLabor(listMaterial);
    }
}
