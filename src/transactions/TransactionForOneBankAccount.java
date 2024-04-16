package transactions;

import accounts.BankAccount;

public interface TransactionForOneBankAccount {
    default void performTransaction(BankAccount bankAccount, double amount) {
        bankAccount.setBalanceAmount(bankAccount.getBalanceAmount() + amount);
        inMemoryDBs.DB.transactions.add(
                DepositTransaction.builder()
                        .sourceAccount(bankAccount)
                        .amount(amount)
                        .transactionTime(java.time.LocalDateTime.now())
                        .transactionStatus(enums.TransactionStatus.COMPLETED)
                        .build());
        System.out.println("Successful");
    }
}
