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
        System.out.println(YELLOW + "| [7] Manage Quote                                    |" + RESET);
        System.out.println(BLUE + "|=====================================================|" + RESET);
        System.out.println(PURPLE + "|                     Material / Labor                |" + RESET);
        System.out.println(BLUE + "|=====================================================|" + RESET);
        System.out.println(YELLOW + "| [8]  Manage Material       [9] Manage Labor         |" + RESET);
        System.out.println(YELLOW + "| [10] Close                                          |" + RESET);
        System.out.println(BLUE + "|=====================================================|" + RESET);
        System.out.print(GREEN + "Enter your option: " + RESET);
    }
    public static void showQuoteMenu() {
        System.out.println(BLUE + "\n|=====================================================|" + RESET);
        System.out.println(PURPLE + "|                   Quote Management                  |" + RESET);
        System.out.println(BLUE + "|====================================================+|" + RESET);
        System.out.println(YELLOW + "| [1] Display All Quotes      [2] Add a Quote         |" + RESET);
        System.out.println(YELLOW + "| [3] Update a Quote          [4] Delete a Quote      |" + RESET);
        System.out.println(BLUE + "|=====================================================|" + RESET);
        System.out.println(YELLOW + "| [5] Exit                                            |" + RESET);
        System.out.println(BLUE + "|=====================================================|" + RESET);
        System.out.print(GREEN + "Enter your choice: " + RESET);
    }

    public static void showUpdateProjectMenu() {
        System.out.println(BLUE + "\n|=======================================================|" + RESET);
        System.out.println(PURPLE + "|                Update Project Options                 |" + RESET);
        System.out.println(BLUE + "|=======================================================|" + RESET);
        System.out.println(YELLOW + "| [1] Update Project Name     [2] Update Project Status |" + RESET);
        System.out.println(YELLOW + "| [3] Update Profit Margin    [4] Exit                  |" + RESET);
        System.out.println(BLUE + "|=======================================================|" + RESET);
        System.out.print(GREEN + "Enter your choice: " + RESET);
    }
    public static void showProjectManageMenu() {
        System.out.println(BLUE + "\n|=====================================================|" + RESET);
        System.out.println(PURPLE + "|                Project Management Options           |" + RESET);
        System.out.println(BLUE + "|=====================================================|" + RESET);
        System.out.println(YELLOW + "| [1] Add Project             [2] Delete Project      |" + RESET);
        System.out.println(YELLOW + "| [3] Update Project          [4] Exit                |" + RESET);
        System.out.println(BLUE + "|=====================================================|" + RESET);
        System.out.print(GREEN + "Choose an option: " + RESET);
    }
}
