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
public class CheckingBankAccount extends BankAccount {

    public static final double MONTHLY_FEE = 13.5;

    public CheckingBankAccount(String accountNumber, BankAccountHolder accountHolder) {
        super(accountNumber, accountHolder, AccountType.CHECKING);
    }

    @Override
    public void runMonthlyUpdate() {

    }

//    public void runMonthlyUpdate() {
//        if (this.getPausedMonthlyBillingsUntil() != null && this.getPausedMonthlyBillingsUntil().isAfter(LocalDateTime.now())) {
//            return;
//        }
//            if (this.getBalanceAmount() < CheckingBankAccount.MONTHLY_FEE) {
//                System.out.println("Monthly fee of " + CheckingBankAccount.MONTHLY_FEE + " for checking account " + this.getAccountNumber() + "has not been deducted due to insufficient funds");
//                return;
//            }
//            this.setBalanceAmount(this.getBalanceAmount() - (CheckingBankAccount.MONTHLY_FEE));
//            Transaction bankServicesBillingTransaction = Transaction.builder()
//                    .amount(CheckingBankAccount.MONTHLY_FEE)
//                    .sourceAccount(this)
//                    .transactionTime(LocalDateTime.now())
//                    .transactionType(TransactionType.BANK_BILLING)
//                    .transactionStatus(TransactionStatus.COMPLETED)
//                    .build();
//            DB.transactions.add(bankServicesBillingTransaction);
//            System.out.println("Monthly fee of " + CheckingBankAccount.MONTHLY_FEE + " for checking account " + this.getAccountNumber() + "has been deducted");
//        }

}
