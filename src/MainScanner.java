import accounts.BankAccount;
import accounts.CheckingBankAccount;
import accounts.SavingsBankAccount;
import enums.AccountType;
import enums.TransactionType;
import inMemoryDBs.DB;
import transactions.Transaction;
import users.BankAccountHolder;
import users.BankEmployee;
import users.BankOwner;
import users.BankUser;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static inMemoryDBs.DB.*;

public class MainScanner {
    public static void main(String[] args) {

        List<BankAccount> bankAccountsFromJSON = DB.fillData("src/jsonFiles/bankAccounts.json", BankAccount.class);
        List<? extends BankUser> bankUsersFromJson = DB.fillData("src/jsonFiles/bankAccountHolders.json", BankAccountHolder.class);
        List<? extends BankUser> bankEmployeesFromJson = DB.fillData("src/jsonFiles/bankEmployees.json", BankEmployee.class);
        List<? extends BankUser> bankOwnersFromJson = DB.fillData("src/jsonFiles/bankOwners.json", BankOwner.class);
        List<BankUser> bankUsersFromJSON = new LinkedList<>();
        bankUsersFromJSON.addAll(bankUsersFromJson);
        bankUsersFromJSON.addAll(bankEmployeesFromJson);
        bankUsersFromJSON.addAll(bankOwnersFromJson);
        System.out.println(bankUsersFromJSON);
        List<Transaction> transactionsFromJSON = DB.fillData("src/jsonFiles/transactions.json", Transaction.class);

        bankAccounts.addAll(bankAccountsFromJSON);
        bankUsers.addAll(bankUsersFromJSON);
        transactions.addAll(transactionsFromJSON);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose as what you want to run the program: \nAccountHolder (A), \nBankEmployee (E), \nBankOwner (O) or \nExit the program (X)");
            String choice = scanner.nextLine();
            if (choice.equals("A")) {
                System.out.println("You chose AccountHolder");
                System.out.println("Enter your account number");
                String accountNumber = scanner.nextLine();
                BankAccountHolder bankAccountHolder = DB.getBankAccountByAccountNumber(accountNumber).getAccountHolder();
                while (true) {
                    System.out.println("Choose what you want to do: \nTransfer money (T), \nDisplay transactions for specific time (D), \nExit to previous menu (X)");
                    String accountHolderChoice = scanner.nextLine();
                    if (accountHolderChoice.equals("T")) {
                        System.out.println("You chose Transfer money \n Enter the amount you want to transfer");
                        double amount = scanner.nextDouble();
                        System.out.println("Enter the destination account number");
                        String destinationAccountNumber = scanner.nextLine();
                        BankAccount destinationAccount = DB.getBankAccountByAccountNumber(destinationAccountNumber);
                        bankAccountHolder.transferMoney(destinationAccount, amount);
                    } else if (accountHolderChoice.equals("D")) {
                        System.out.println("You chose Display transactions for specific time");
                        System.out.println("Enter the start date in the format yyyy-MM-ddTHH:mm:ss");
                        String startDate = scanner.nextLine();
                        System.out.println("Enter the end date in the format yyyy-MM-ddTHH:mm:ss");
                        String endDate = scanner.nextLine();
                        bankAccountHolder.getTransactionListForSpecificTime(startDate, endDate);

                    } else if (accountHolderChoice.equals("X")) {
                        System.out.println("You chose Exit");
                        break;
                    } else {
                        System.out.println("Invalid choice, please try again");
                    }
                }
            } else if (choice.equals("E")) {
                System.out.println("You chose BankEmployee\n Enter your first name");
                String firstName = scanner.nextLine();
                System.out.println("Enter your last name");
                String lastName = scanner.nextLine();
                BankEmployee bankEmployee = (BankEmployee) DB.getUserByFullName(firstName, lastName);
                while (true) {
                    System.out.println("Choose what you want to do: \nAdd bank account (A), \nDisplay bank account information (D), \nUpdate bank account balance (U), \nRun monthly update (R), \nExit to go to previous menu(X)");
                    String bankEmployeeChoice = scanner.nextLine();
                    if (bankEmployeeChoice.equals("A")) {
                        System.out.println("You chose Add bank account\n Enter the account number");
                        String accountNumber = scanner.nextLine();
                        System.out.println("Enter the account holder's first name");
                        String accountHolderFirstName = scanner.nextLine();
                        System.out.println("Enter the account holder's last name");
                        String accountHolderLastName = scanner.nextLine();
                        System.out.println("Enter the account holder's email");
                        String accountHolderEmail = scanner.nextLine();
                        System.out.println("Enter the account holder's phone number");
                        String accountHolderPhoneNumber = scanner.nextLine();
                        System.out.println("Enter the account type: Savings (S) or Checking (C)");
                        String accountType = scanner.nextLine();
                        BankAccountHolder accountHolder = new BankAccountHolder(accountHolderFirstName, accountHolderLastName, accountHolderEmail, accountHolderPhoneNumber);
                        if (accountType.equals("S")) {
                            System.out.println("Enter the minimum balance");
                            double minimumBalance = scanner.nextDouble();
                            SavingsBankAccount savingsBankAccount = new SavingsBankAccount(accountNumber, accountHolder, minimumBalance);
                            bankAccounts.add(savingsBankAccount);
                        } else if (accountType.equals("C")) {
                            CheckingBankAccount bankAccount = new CheckingBankAccount(accountNumber, accountHolder);
                            bankAccounts.add(bankAccount);
                        } else {
                            System.out.println("Invalid account type");
                        }
                    } else if (bankEmployeeChoice.equals("D")) {
                        System.out.println("You chose Display bank account information");
                        System.out.println("Enter the account number");
                        String accountNumber = scanner.nextLine();
                        bankEmployee.displayInformationForBankAccount(accountNumber);
                    } else if (bankEmployeeChoice.equals("U")) {
                        System.out.println("You chose Update bank account balance");
                        System.out.println("Enter the account number");
                        String accountNumber = scanner.nextLine();
                        BankAccount bankAccount = DB.getBankAccountByAccountNumber(accountNumber);
                        System.out.println("Enter the transaction type: Deposit (D) or Withdrawal (W)");
                        String transactionType = scanner.nextLine();
                        System.out.println("Enter the amount");
                        double amount = scanner.nextDouble();
                        if (transactionType.equals("D")) {
                            bankEmployee.updateBankAccountBalance(bankAccount, TransactionType.DEPOSIT, amount);
                        } else if (transactionType.equals("W")) {
                            bankEmployee.updateBankAccountBalance(bankAccount, TransactionType.WITHDRAWAL, amount);
                        } else {
                            System.out.println("Invalid transaction type");
                        }
                    } else if (bankEmployeeChoice.equals("R")) {
                        System.out.println("You chose Run monthly update");
                        bankEmployee.runMonthlyUpdateForAllAccounts();
                    } else if (bankEmployeeChoice.equals("X")) {
                        System.out.println("You chose Exit");
                        break;
                    } else {
                        System.out.println("Invalid choice");
                    }
                }
            } else if (choice.equals("O")) {
                System.out.println("You chose BankOwner");
                System.out.println("Enter your first name");
                String firstName = scanner.nextLine();
                System.out.println("Enter your last name");
                String lastName = scanner.nextLine();
                BankOwner bankOwner = (BankOwner) DB.getUserByFullName(firstName, lastName);
                while (true) {
                    System.out.println("Choose what you want to do: \nGet total income (T), \nGet yearly income (Y), \nGet income from accounts by account type (I), \nGet all accounts (A), \nGet income from user (U), \nGet top five paying savings accounts (F), \nPause monthly rate for specific amount (P), \nExit to go to previous menu (X)");
                    String bankOwnerChoice = scanner.nextLine();
                    if (bankOwnerChoice.equals("T")) {
                        System.out.println("You chose Get total yearly income");
                        System.out.println("Total yearly bank income: " + Util.formatToTwoDecimals(bankOwner.getTotalYearlyIncome()));
                    } else if (bankOwnerChoice.equals("Y")) {
                        System.out.println("You chose Get yearly income");
                        System.out.println("Enter the year");
                        int year = scanner.nextInt();
                        System.out.println("Income for year " + year + ": " + Util.formatToTwoDecimals(bankOwner.getIncomeForYear(year)));
                    } else if (bankOwnerChoice.equals("I")) {
                        System.out.println("You chose Get income from accounts by account type");
                        System.out.println("Enter the account type: Savings (S) or Checking (C)");
                        String accountType = scanner.nextLine();
                        AccountType type = accountType.equals("S") ? AccountType.SAVINGS : AccountType.CHECKING;
                        System.out.println("Income from " + accountType + " accounts: " + Util.formatToTwoDecimals(bankOwner.getIncomeFromAccountsByAccountType(type)));
                    } else if (bankOwnerChoice.equals("A")) {
                        System.out.println("You chose Get all accounts");
                        System.out.println("All accounts: " + bankOwner.getAllAccounts());
                    } else if (bankOwnerChoice.equals("U")) {
                        System.out.println("You chose Get income from user");
                        System.out.println("Enter the user's bank account number");
                        String accountNumber = scanner.nextLine();
                        BankAccountHolder bankAccountHolder = DB.getBankAccountByAccountNumber(accountNumber).getAccountHolder();
                        System.out.println("Income from user: " + bankAccountHolder + " " + Util.formatToTwoDecimals(bankOwner.getIncomeFromUser(bankAccountHolder)));
                    } else if (bankOwnerChoice.equals("F")) {
                        System.out.println("You chose Get top five paying savings accounts");
                        System.out.println("Top five paying savings accounts: " + bankOwner.getTopFivePayingSavingsAccounts());
                    } else if (bankOwnerChoice.equals("P")) {
                        System.out.println("You chose Pause monthly rates");
                        System.out.println("Enter the user's bank account number, whose monthly rates you want to pause");
                        String accountNumber = scanner.nextLine();
                        BankAccount bankAccount = DB.getBankAccountByAccountNumber(accountNumber);
                        System.out.println("Enter the date up to which you want to pause the rates for this user in the format yyyy-MM-ddTHH:mm:ss");
                        String date = scanner.nextLine();
                        LocalDateTime localDateTime = LocalDateTime.parse(date);
                        bankOwner.pauseMonthlyRates(bankAccount, localDateTime);
                    } else if (choice.equals("X")) {
                        System.out.println("You chose Exit");
                        break;
                    } else {
                        System.out.println("Invalid choice");
                    }
                }
            }
            if (choice.equals("X")) {
                break;
            }
        }
        System.out.println("You have chosen to exit the program");
    }
}
