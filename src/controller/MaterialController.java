package controller;

import domain.Material;
import domain.Project;
import service.MaterialService;
import utils.ComponentType;

import java.util.Optional;
import java.util.Scanner;

public class MaterialController {
    private final MaterialService materialService = new MaterialService();
    private final Scanner scanner = new Scanner(System.in);
    public Optional<Material> save(Project project){
        System.out.print("Do you want to add a material? (yes/no)");
        String option = scanner.nextLine();
        if(option.equals("yes")){
            System.out.print("Enter the material name : ");
            String name = scanner.nextLine();
            System.out.print("Enter the quantity of this material (in m²): ");
            double quantity = scanner.nextDouble();
            System.out.print("Enter the unit cost of this material (€/m²): ");
            double unitCost = scanner.nextDouble();
            System.out.print("Enter the cost of transporting this material (€): ");
            double transportCost = scanner.nextDouble();
            System.out.print("Enter the material quality coefficient (1.0 = standard, > 1.0 = high quality):");
            double coefficientQuality = scanner.nextDouble();
            String def = scanner.nextLine();
            Material material = new Material(name,String.valueOf(ComponentType.MATERIAL),0,unitCost,quantity,transportCost,coefficientQuality,project);
            materialService.save(material).ifPresentOrElse(material1 -> {
                System.out.println("Material added successfully");
            },()-> {
                System.out.println("Material not added");
            });
            return Optional.of(material);
        }else {
            System.out.println("Material Add Cancelled");
            return Optional.empty();
        }

    }
    public void updateVAT(Project project){
        System.out.print("Enter the VAT rate : ");
        double vatRate = scanner.nextDouble();
        materialService.updateVAT(project,vatRate);
    }


}
