package utils;

public class Menu {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";

    public void showMenu() {
        System.out.println(BLUE + "\n|=====================================================|" + RESET);
        System.out.println(PURPLE + "|                   Client Management                 |" + RESET);
        System.out.println(BLUE + "|====================================================+|" + RESET);
        System.out.println(YELLOW + "| [1] Create New Client      [2] Display All Clients  |" + RESET);
        System.out.println(YELLOW + "| [3] Update Client          [4] Delete Client        |" + RESET);
        System.out.println(BLUE + "|=====================================================|" + RESET);
        System.out.println(PURPLE + "|                   Project Management                |" + RESET);
        System.out.println(BLUE + "|=====================================================|" + RESET);
        System.out.println(YELLOW + "| [5] Manage Project         [6] Display All Projects |" + RESET);
        System.out.println(YELLOW + "| [7] Add New Quote                                   |" + RESET);
        System.out.println(BLUE + "|=====================================================|" + RESET);
        System.out.println(PURPLE + "|                     Material / Labor                |" + RESET);
        System.out.println(BLUE + "|=====================================================|" + RESET);
        System.out.println(YELLOW + "| [8]  Manage Material       [9] Manage Labor         |" + RESET);
        System.out.println(YELLOW + "| [10] Close                                          |" + RESET);
        System.out.println(BLUE + "|=====================================================|" + RESET);
        System.out.print(GREEN + "Enter your option: " + RESET);
    }
}
