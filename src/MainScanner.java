import java.util.Scanner;

public class MainScanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose as what you want to run the program: AccountHolder (A), BankEmployee (E), BankOwner (O) or Exit (X)");
            String choice = scanner.nextLine();
            if (choice.equals("A")) {
                System.out.println("You chose AccountHolder");
                while (true) {
                    System.out.println("Choose what you want to do: Transfer money (T), Display transactions (D), Exit (E)");
                    String accountHolderChoice = scanner.nextLine();
                    if (accountHolderChoice.equals("T")) {
                        System.out.println("You chose Transfer money");
                    } else if (accountHolderChoice.equals("D")) {
                        System.out.println("You chose Display transactions");
                    } else if (accountHolderChoice.equals("E")) {
                        System.out.println("You chose Exit");
                        break;
                    } else {
                        System.out.println("Invalid choice");
                    }
                }
            } else if (choice.equals("E")) {
                System.out.println("You chose BankEmployee");
                while (true) {
                    System.out.println("Choose what you want to do: Add bank account (A), Display bank account information (D), Update bank account balance (U), Run monthly update (R), Exit (E)");
                    String bankEmployeeChoice = scanner.nextLine();
                    if (bankEmployeeChoice.equals("A")) {
                        System.out.println("You chose Add bank account");
                    } else if (bankEmployeeChoice.equals("D")) {
                        System.out.println("You chose Display bank account information");

                    } else if (bankEmployeeChoice.equals("U")) {
                        System.out.println("You chose Update bank account balance");
                    } else if (bankEmployeeChoice.equals("R")) {
                        System.out.println("You chose Run monthly update");
                    } else if (bankEmployeeChoice.equals("E")) {
                        System.out.println("You chose Exit");
                        break;
                    } else {
                        System.out.println("Invalid choice");
                    }
                }
            } else if (choice.equals("O")) {
                System.out.println("You chose BankOwner");
                while (true) {
                    System.out.println("Choose what you want to do: Get total yearly income (T), Get income from accounts by account type (I), Get all accounts (A), Get income from user (U), Get top five paying savings accounts (F), Pause monthly rates (P), Exit (X)");
                    String bankOwnerChoice = scanner.nextLine();
                    if (bankOwnerChoice.equals("T")) {
                        System.out.println("You chose Get total yearly income");
                    } else if (bankOwnerChoice.equals("I")) {
                        System.out.println("You chose Get income from accounts by account type");
                    } else if (bankOwnerChoice.equals("A")) {
                        System.out.println("You chose Get all accounts");
                    } else if (bankOwnerChoice.equals("U")) {
                        System.out.println("You chose Get income from user");
                    } else if (bankOwnerChoice.equals("F")) {
                        System.out.println("You chose Get top five paying savings accounts");
                    } else if (bankOwnerChoice.equals("P")) {
                        System.out.println("You chose Pause monthly rates");
                    } else if (choice.equals("X")) {
                        System.out.println("You chose Exit");
                        break;
                    } else {
                        System.out.println("Invalid choice");
                    }
                }
            }
        }
    }
}
