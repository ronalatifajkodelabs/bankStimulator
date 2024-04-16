import accounts.BankAccount;
import accounts.CheckingBankAccount;
import accounts.SavingsBankAccount;
import enums.AccountType;
import inMemoryDBs.DB;
import transactions.Transaction;
import users.BankAccountHolder;
import users.BankEmployee;
import users.BankOwner;
import users.BankUser;

import java.util.LinkedList;
import java.util.List;

import static inMemoryDBs.DB.*;

public class Main {
    public static void main(String[] args) {

        //TODO me ja dergu vetit nuk bon same account to same account ✅
        //TODO mos me lon userin me transferu me shume se minimum balance ✅

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

        System.out.println(bankAccountsFromJSON.size());
        System.out.println(bankUsersFromJSON.size());
        System.out.println(transactionsFromJSON.size());

        BankAccountHolder bankAccountHolder = (BankAccountHolder) bankUsers.getFirst();
        System.out.println(bankAccountHolder);
        BankAccountHolder bankAccountHolder1 = (BankAccountHolder) bankUsers.get(1);
        System.out.println(bankAccountHolder1);
        System.out.println(bankUsers.get(2));
        BankAccount destinationAccount = DB.getBankAccountByAccountHolder((BankAccountHolder) bankUsers.get(2));
        bankAccountHolder.transferMoney(destinationAccount, 1000);

        System.out.println(DB.transactions.getLast());
        System.out.println(DB.transactions.size());

        BankAccount bankAccountOfBrittany = DB.getBankAccountByAccountNumber("KM3474065515931294812609625");
        BankAccount bankAccountOfJulie = DB.getBankAccountByAccountNumber("ME43866654030989286926");
        bankAccountHolder.getTransactionListForSpecificTime("2021-09-01T00:00:00", "2024-06-30T23:59:59");

        BankEmployee bankEmployee = (BankEmployee) DB.getUserByFullName("Cassandra", "Moore");
        bankEmployee.displayInformationForBankAccount("KM3474065515931294812609625");
        bankEmployee.runMonthlyUpdateForAllAccounts();

        BankOwner bankOwner = (BankOwner) DB.getUserByFullName("Gina", "Koelpin");
        System.out.println("Total yearly bank income: " + Util.formatToTwoDecimals(bankOwner.getTotalYearlyIncome()));

        System.out.println("----------------------------------------------");
        System.out.println("Income from checking accounts: " + Util.formatToTwoDecimals(bankOwner.getIncomeFromAccountsByAccountType(AccountType.CHECKING)));
        System.out.println("Income from savings accounts: " + Util.formatToTwoDecimals(bankOwner.getIncomeFromAccountsByAccountType(AccountType.SAVINGS)));

        System.out.println("Income from checking accounts: " + Util.formatToTwoDecimals(bankOwner.getIncomeFromAccountsByAccountTypeGeneric(SavingsBankAccount.class)));
        System.out.println("Income from savings accounts: " + Util.formatToTwoDecimals(bankOwner.getIncomeFromAccountsByAccountTypeGeneric(CheckingBankAccount.class)));
        System.out.println("----------------------------------------------");

        System.out.println("All accounts: " + bankOwner.getAllAccounts());
        System.out.println("Income from user: " + bankAccountHolder1 + " " + Util.formatToTwoDecimals(bankOwner.getIncomeFromUser(bankAccountHolder1)));
        System.out.println("Top five paying savings accounts: " + bankOwner.getTopFivePayingSavingsAccounts());
//        bankOwner.pauseMonthlyRates(bankAccountOfBrittany, Util.createLocalDateTimeFromString("2025-01-01T00:00:00"));


    }
}
