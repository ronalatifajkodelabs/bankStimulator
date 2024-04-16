package transactions;

import accounts.BankAccount;

public interface TransactionForTwoBankAccounts {
    default void performTransaction(BankAccount sourceAccount, BankAccount targetAccount, double amount) {
        if (sourceAccount == targetAccount) {
            System.out.println("You can't transfer money to the same account");
            return;
        }
        if (sourceAccount == null || targetAccount == null) {
            System.out.println("At least one of the accounts was not found");
            return;
        }
        if (sourceAccount.getAccountType().equals(enums.AccountType.SAVINGS)) {
            if (amount > ((accounts.SavingsBankAccount) sourceAccount).getMinimumBalance()) {
                System.out.println("You can't transfer more than the minimum balance of the account");
            }
        }
        sourceAccount.setBalanceAmount(sourceAccount.getBalanceAmount() - amount);
        targetAccount.setBalanceAmount(targetAccount.getBalanceAmount() + amount);
        inMemoryDBs.DB.transactions.add(
                TransferTransaction.builder()
                        .sourceAccount(sourceAccount)
                        .amount(amount)
                        .transactionTime(java.time.LocalDateTime.now())
                        .transactionStatus(enums.TransactionStatus.COMPLETED)
                        .destinationAccount(targetAccount)
                        .build());
        System.out.println("Successful");
    }
}
