package utils;

public class Menu {

    public void showMenu() {
        System.out.println(Cl.BLUE + "\n|=====================================================|" + Cl.RESET);
        System.out.println(Cl.PURPLE + "|    Kitchen Renovation Cost Estimation Application   |" + Cl.RESET);
        System.out.println(Cl.BLUE + "|====================================================+|" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [1] Client Manager         [2] Project Manager      |" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [3] Display Project        [4] Quote Manager        |" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [5] Manage Material        [6] Manage Labor         |" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [7] Close                                          |" + Cl.RESET);
        System.out.println(Cl.BLUE + "|=====================================================|" + Cl.RESET);
        System.out.print(Cl.GREEN + "Enter your option: " + Cl.RESET);
    }
    public static void showQuoteMenu() {
        System.out.println(Cl.BLUE + "\n|=====================================================|" + Cl.RESET);
        System.out.println(Cl.PURPLE + "|                   Quote Management                  |" + Cl.RESET);
        System.out.println(Cl.BLUE + "|====================================================+|" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [1] Display All Quotes      [2] Add a Quote         |" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [3] Update a Quote          [4] Delete a Quote      |" + Cl.RESET);
        System.out.println(Cl.BLUE + "|=====================================================|" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [5] Exit                                            |" + Cl.RESET);
        System.out.println(Cl.BLUE + "|=====================================================|" + Cl.RESET);
        System.out.print(Cl.GREEN + "Enter your choice: " + Cl.RESET);
    }

    public static void showUpdateProjectMenu() {
        System.out.println(Cl.BLUE + "\n|=======================================================|" + Cl.RESET);
        System.out.println(Cl.PURPLE + "|                Update Project Options                 |" + Cl.RESET);
        System.out.println(Cl.BLUE + "|=======================================================|" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [1] Update Project Name     [2] Update Project Status |" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [3] Update Profit Margin    [4] Exit                  |" + Cl.RESET);
        System.out.println(Cl.BLUE + "|=======================================================|" + Cl.RESET);
        System.out.print(Cl.GREEN + "Enter your choice: " + Cl.RESET);
    }
    public static void showProjectManageMenu() {
        System.out.println(Cl.BLUE + "\n|=====================================================|" + Cl.RESET);
        System.out.println(Cl.PURPLE + "|                Project Management Options           |" + Cl.RESET);
        System.out.println(Cl.BLUE + "|=====================================================|" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [1] Add Project             [2] Delete Project      |" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [3] Update Project          [4] Exit                |" + Cl.RESET);
        System.out.println(Cl.BLUE + "|=====================================================|" + Cl.RESET);
        System.out.print(Cl.GREEN + "Choose an option: " + Cl.RESET);
    }

    public static void clientManager() {
        System.out.println(Cl.BLUE + "\n|=======================================================|" + Cl.RESET);
        System.out.println(Cl.PURPLE + "|                    Manage Client                      |" + Cl.RESET);
        System.out.println(Cl.BLUE + "|=======================================================|" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [1] Add New Client          [2] Display Client        |" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [3] Update Client           [4] Delete Client         |" + Cl.RESET);
        System.out.println(Cl.YELLOW + "| [5] Exit                                              |" + Cl.RESET);
        System.out.println(Cl.BLUE + "|=======================================================|" + Cl.RESET);
        System.out.print(Cl.GREEN + "Enter your choice: " + Cl.RESET);
    }
}
