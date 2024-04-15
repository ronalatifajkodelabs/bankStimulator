package users;

import accounts.BankAccount;
import accounts.Transaction;
import enums.AccountType;
import enums.TransactionStatus;
import enums.TransactionType;
import inMemoryDBs.DB;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class BankAccountHolder extends BankUser {

    public BankAccountHolder(String firstName, String lastName, String email, String phoneNumber) {
        super(firstName, lastName, email, phoneNumber);
    }

    static final BankAccountHolder bank = new BankAccountHolder("BANK", "BANK", "BANK", "BANK");

    /*TODO
    transferMoney ✅
    getTransactionListForSpecificTime ✅
     */

    public void transferMoney(BankAccount sourceAccount, BankAccount destinationAccount, double amount) {
        if (sourceAccount == destinationAccount) {
            System.out.println("You can't transfer money to the same account");
            return;
        }
        if (sourceAccount == null || destinationAccount == null) {
            System.out.println("At least one of the accounts was not found");
            return;
        }
        if (sourceAccount.getAccountType().equals(AccountType.SAVINGS)) {
            if (amount > sourceAccount.getMinimumBalance()) {
                System.out.println("You can't transfer more than the minimum balance of the account");
            }
        }
        if (sourceAccount.getBalanceAmount() < amount) {
            Transaction transaction = Transaction.builder()
                    .sourceAccount(sourceAccount)
                    .destinationAccount(destinationAccount)
                    .amount(amount)
                    .transactionType(TransactionType.TRANSFER)
                    .transactionTime(java.time.LocalDateTime.now())
                    .transactionStatus(TransactionStatus.FAILED)
                    .build();
            DB.transactions.add(transaction);
            System.out.println("Insufficient funds");
        } else {
            sourceAccount.setBalanceAmount(sourceAccount.getBalanceAmount() - amount);
            destinationAccount.setBalanceAmount(destinationAccount.getBalanceAmount() + amount);
            DB.transactions.add(Transaction.builder()
                    .sourceAccount(sourceAccount)
                    .destinationAccount(destinationAccount)
                    .amount(amount)
                    .transactionType(TransactionType.TRANSFER)
                    .transactionTime(java.time.LocalDateTime.now())
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .build());
            System.out.println("Transfer successful");
        }
    }

    public void transferMoney(String sourceAccountNumber, String destinationAccountNumber, double amount) {
        BankAccount sourceAccount = DB.getBankAccountByAccountNumber(sourceAccountNumber);
        BankAccount destinationAccount = DB.getBankAccountByAccountNumber(destinationAccountNumber);
        transferMoney(sourceAccount, destinationAccount, amount);
    }

    public void transferMoney(BankAccount destinationAccount, double amount) {
        BankAccount sourceAccount = DB.getBankAccountByAccountHolder(this);
        if (sourceAccount == null) {
            System.out.println("Account not found");
            return;
        }
        transferMoney(sourceAccount, destinationAccount, amount);
    }

    //TODO test if this one has good indentation because i used method reference ✅
    private void getTransactionListForSpecificTime(BankAccount bankAccount, String startTime, String endTime) {
        System.out.println("Transaction list for " + bankAccount.getAccountNumber() + " between " + startTime + " and " + endTime);
        DB.transactions.stream()
                .filter(transaction -> (transaction.getSourceAccount().equals(bankAccount) || (transaction.getDestinationAccount() != null && transaction.getDestinationAccount().equals(bankAccount)))
                        && transaction.getTransactionTime().isAfter(java.time.LocalDateTime.parse(startTime)) && transaction.getTransactionTime().isBefore(java.time.LocalDateTime.parse(endTime)))
                .forEach(System.out::println);
    }

    public void getTransactionListForSpecificTime(String startTime, String endTime) {
        BankAccount bankAccount = DB.getBankAccountByAccountHolder(this);
        if (bankAccount == null) {
            System.out.println("Account not found");
            return;
        }
        getTransactionListForSpecificTime(bankAccount, startTime, endTime);
    }

}
