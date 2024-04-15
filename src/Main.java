import accounts.Transaction;
import enums.AccountType;
import accounts.BankAccount;
import inMemoryDBs.DB;
import users.BankAccountHolder;
import users.BankEmployee;
import users.BankOwner;

import static inMemoryDBs.DB.bankAccounts;

public class Main {
    public static void main(String[] args) {

        //TODO me ja dergu vetit nuk bon same account to same account ✅
        //TODO mos me lon userin me transferu me shume se minimum balance ✅

        DB db = new DB();

//        db.fillBankAccounts("src/jsonFiles/bankAccounts.json", BankAccount.class);
        DB.fillData("src/jsonFiles/bankAccountHolders.json", BankAccountHolder.class);
        DB.fillData("src/jsonFiles/bankEmployees.json", BankEmployee.class);
        DB.fillData("src/jsonFiles/bankOwners.json", BankOwner.class);

        System.out.println(bankAccounts.size());
        System.out.println(DB.bankUsers.size());
        System.out.println(DB.transactions.size());

        BankAccountHolder bankAccountHolder = (BankAccountHolder) DB.bankUsers.getFirst();
        BankAccountHolder bankAccountHolder1 = (BankAccountHolder) DB.bankUsers.get(1);
        BankAccountHolder bankAccountHolder2 = (BankAccountHolder) DB.bankUsers.get(2);
        BankAccount sourceAccount = DB.getBankAccountByAccountHolder(bankAccountHolder2);
        bankAccountHolder.transferMoney(sourceAccount, 1000);

//        System.out.println(DB.transactions.getLast());
//        System.out.println(DB.transactions.size());

        BankAccount bankAccountOfBrittany = DB.getBankAccountByAccountNumber("KM3474065515931294812609625");
        BankAccount bankAccountOfJulie = DB.getBankAccountByAccountNumber("ME43866654030989286926");
        bankAccountHolder.getTransactionListForSpecificTime("2021-09-01T00:00:00", "2024-06-30T23:59:59");

        BankEmployee bankEmployee = (BankEmployee) DB.getUserByFullName("Cassandra", "Moore");
        bankEmployee.displayInformationForBankAccount("KM3474065515931294812609625");
        bankEmployee.runMonthlyUpdateForAllAccounts();

        BankOwner bankOwner = (BankOwner) DB.getUserByFullName("Gina", "Koelpin");
        System.out.println("Total yearly bank income: " + Util.formatToTwoDecimals(bankOwner.getTotalYearlyIncome()));
        System.out.println("Income from checking accounts: " + Util.formatToTwoDecimals(bankOwner.getIncomeFromAccountsByAccountType(AccountType.CHECKING)));
        System.out.println("Income from savings accounts: " + Util.formatToTwoDecimals(bankOwner.getIncomeFromAccountsByAccountType(AccountType.SAVINGS)));
        System.out.println("All accounts: " + bankOwner.getAllAccounts());
        System.out.println("Income from user: " + bankAccountHolder1 + " " + Util.formatToTwoDecimals(bankOwner.getIncomeFromUser(bankAccountHolder1)));
        System.out.println("Top five paying savings accounts: " + bankOwner.getTopFivePayingSavingsAccounts());
//        bankOwner.pauseMonthlyRates(bankAccountOfBrittany, Util.createLocalDateTimeFromString("2025-01-01T00:00:00"));


    }
}
