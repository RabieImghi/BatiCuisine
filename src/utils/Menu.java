package utils;

public class Menu {
    public void showMenu() {
        System.out.println("\n|====================================================+|");
        System.out.println("|                   Client Management                 |");
        System.out.println("|====================================================+|");
        System.out.println("| [1] Create New Client      [2] Display All Client   |");
        System.out.println("| [3] Delete Client          [4] Update Client        |");
        System.out.println("|=====================================================|");
        System.out.println("|                   Project Management                |");
        System.out.println("|=====================================================|");
        System.out.println("| [5] Create New Project     [6] Display All Project  |");
        System.out.println("| [7] Calculate The Cost Of A Project                 |");
        System.out.println("|=====================================================|");
        System.out.println("|                     Material / Labor                |");
        System.out.println("|=====================================================|");
        System.out.println("| [8] Create New Material    [9] Create New Labor     |");
        System.out.println("| [10] Close                                          |");
        System.out.println("|=====================================================|");
        System.out.print("Enter your option: ");
    }
}
