package utils;

public class Menu {
    public void showMenu() {
        System.out.println("\n|=====================================================|");
        System.out.println("|                   Client Management                 |");
        System.out.println("|====================================================+|");
        System.out.println("| [1] Create New Client      [2] Display All Client   |");
        System.out.println("| [3] Update Client          [4] Delete Client        |");
        System.out.println("|=====================================================|");
        System.out.println("|                   Project Management                |");
        System.out.println("|=====================================================|");
        System.out.println("| [5] Manage Project         [6] Display All Project  |");
        System.out.println("| [7] Add New Quote                                   |");
        System.out.println("|=====================================================|");
        System.out.println("|                     Material / Labor                |");
        System.out.println("|=====================================================|");
        System.out.println("| [8]  Manage Material       [9] Manage Labor         |");
        System.out.println("| [10] Close                                          |");
        System.out.println("|=====================================================|");
        System.out.print("Enter your option: ");
    }
}
