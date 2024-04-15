package users;

import enums.AccountType;
import accounts.BankAccount;
import accounts.Transaction;
import enums.TransactionStatus;
import enums.TransactionType;
import inMemoryDBs.DB;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class BankOwner extends BankUser {

    public BankOwner(String firstName, String lastName, String email, String phoneNumber) {
        super(firstName, lastName, email, phoneNumber);
    }

    /*TODO
    getTotalYearlyIncome,
    getIncomeFromCheckingAccounts,
    getIncomeFromSavingsAccounts,
    getAllAccounts
    getIncomeFromUser
    getTopFivePayingSavingsAccounts
    pauseMonthlyRates*/

    public double getTotalYearlyIncome() {
        return DB.transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    //TODO ktu i kom combine getIncomeFromCheckingAccounts edhe getIncomeFromSavingsAccounts
    public double getIncomeFromAccountsByAccountType(AccountType accountType) {
        return DB.transactions.stream()
                .filter(transaction -> transaction.getSourceAccount().getAccountType() != null && transaction.getSourceAccount().getAccountType().equals(accountType)
                        && transaction.getTransactionType().equals(TransactionType.BANK_BILLING) && transaction.getTransactionStatus().equals(TransactionStatus.COMPLETED))
                .mapToDouble(Transaction::getAmount)
                .sum();

        //xTODO if this were geniric how to change it?
    }

    //TODO me marr prej json ose me kriju ni klas bank account db e aty mi shti in memory list on creation
    public List<BankAccount> getAllAccounts() {
        return DB.bankAccounts;
    }

    public double getIncomeFromUser(BankUser bankUser) {
        return DB.transactions.stream()
                .filter(transaction -> transaction.getSourceAccount().getAccountHolder().equals(bankUser))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    //TODO test this - had issues with making this a nested stream
    public List<BankAccount> getTopFivePayingSavingsAccounts() {
        Map<BankAccount, Double> bankAccountToTotalAmountBilled = new HashMap<>();

        for (BankAccount bankAccount : DB.bankAccounts) {
            if (bankAccount.getAccountType().equals(AccountType.SAVINGS)) {
                List<Transaction> transactions = DB.transactions.stream()
                        .filter(transaction -> transaction.getSourceAccount().equals(bankAccount))
                        .toList();
                double totalAmount = transactions.stream()
                        .mapToDouble(Transaction::getAmount)
                        .sum();
                bankAccountToTotalAmountBilled.put(bankAccount, totalAmount);
            }
        }
        List<Map.Entry<BankAccount, Double>> listToSortMap = new LinkedList<>(bankAccountToTotalAmountBilled.entrySet());
        listToSortMap.sort(Map.Entry.comparingByValue());
        Collections.reverse(listToSortMap);

        return listToSortMap.stream()
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
    }

    public void pauseMonthlyRates(BankAccount bankAccount, LocalDateTime pausedUntil) {
        bankAccount.setPausedMonthlyBillingsUntil(pausedUntil);
    }

}
