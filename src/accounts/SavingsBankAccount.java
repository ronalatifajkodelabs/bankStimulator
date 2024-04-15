package accounts;

import enums.AccountType;
import enums.TransactionStatus;
import enums.TransactionType;
import inMemoryDBs.DB;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import users.BankAccountHolder;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SavingsBankAccount extends BankAccount {

    public static final double INTEREST_RATE = 0.0399;
    private double minimumBalance;

    public SavingsBankAccount(String accountNumber, BankAccountHolder accountHolder, double minimumBalance) {
        super(accountNumber, accountHolder, AccountType.SAVINGS);
        this.minimumBalance = minimumBalance;
    }

    public void runMonthlyUpdate() {
        if (this.getBalanceAmount() < (this.getBalanceAmount() * SavingsBankAccount.INTEREST_RATE)) {
            System.out.println("Monthly fee of " + (this.getBalanceAmount() - (this.getBalanceAmount() * SavingsBankAccount.INTEREST_RATE)) + " for saving account " + this.getAccountNumber() + "has not been deducted due to insufficient funds");
            return;
        }
        System.out.println("Monthly fee of " + this.getBalanceAmount() * SavingsBankAccount.INTEREST_RATE + " for saving account " + this.getAccountNumber() + "has been deducted");
        Transaction bankServicesBillingTransaction = Transaction.builder()
                .amount(this.getBalanceAmount() * SavingsBankAccount.INTEREST_RATE)
                .sourceAccount(this)
                .transactionTime(LocalDateTime.now())
                .transactionType(TransactionType.BANK_BILLING)
                .transactionStatus(TransactionStatus.COMPLETED)
                .build();
        this.setBalanceAmount(this.getBalanceAmount() - (this.getBalanceAmount() * SavingsBankAccount.INTEREST_RATE));
        DB.transactions.add(bankServicesBillingTransaction);
    }
}

