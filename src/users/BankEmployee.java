package users;

import accounts.*;
import enums.TransactionStatus;
import enums.TransactionType;
import inMemoryDBs.DB;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BankEmployee extends BankUser {

    public BankEmployee(String firstName, String lastName, String email, String phoneNumber) {
        super(firstName, lastName, email, phoneNumber);
    }

    /*TODO
    addBankAccount ✅
    displayInformationForBankAccount ✅
    updateBankAccountBalance ✅
    runMonthlyUpdate ✅
     */

    private SavingsBankAccount addSavingsBankAccount(String accountNumber, BankAccountHolder accountHolder, double minimumBalance) {
        if (minimumBalance < 0) {
            System.out.println("Minimum balance can't be negative");
            return null;
        }
        SavingsBankAccount account = new SavingsBankAccount(accountNumber, accountHolder, minimumBalance);
        inMemoryDBs.DB.bankAccounts.add(account);
        return account;
    }

    private CheckingBankAccount addCheckingBankAccount(String accountNumber, BankAccountHolder accountHolder) {
        CheckingBankAccount account = new CheckingBankAccount(accountNumber, accountHolder);
        inMemoryDBs.DB.bankAccounts.add(account);
        return account;
    }

    private void displayInformationForBankAccount(BankAccount bankAccount) {
        System.out.println(bankAccount.toString());
    }

    public void displayInformationForBankAccount(String accountNumber) {
        for (BankAccount bankAccount : inMemoryDBs.DB.bankAccounts) {
            if (bankAccount.getAccountNumber().equals(accountNumber)) {
                System.out.println(bankAccount);
                return;
            }
        }
        System.out.println("Account not found");
    }

    public void updateBankAccountBalance(BankAccount bankAccount, TransactionType transactionType, double amount) {
        if (transactionType == TransactionType.DEPOSIT) {
            bankAccount.setBalanceAmount(bankAccount.getBalanceAmount() + amount);
            inMemoryDBs.DB.transactions.add(
                    Transaction.builder()
                            .destinationAccount(bankAccount)
                            .amount(amount)
                            .transactionType(TransactionType.DEPOSIT)
                            .transactionTime(java.time.LocalDateTime.now())
                            .transactionStatus(TransactionStatus.COMPLETED)
                            .build());
            System.out.println("Deposit successful");
        } else if (transactionType == TransactionType.WITHDRAWAL) {
            if (bankAccount.getBalanceAmount() < amount) {
                DB.transactions.add(
                        Transaction.builder()
                                .sourceAccount(bankAccount)
                                .amount(amount)
                                .transactionType(TransactionType.WITHDRAWAL)
                                .transactionTime(java.time.LocalDateTime.now())
                                .transactionStatus(TransactionStatus.FAILED)
                                .build());
                System.out.println("Insufficient funds");
            } else {
                bankAccount.setBalanceAmount(bankAccount.getBalanceAmount() - amount);
                DB.transactions.add(
                        Transaction.builder()
                                .sourceAccount(bankAccount)
                                .amount(amount)
                                .transactionType(TransactionType.WITHDRAWAL)
                                .transactionTime(java.time.LocalDateTime.now())
                                .transactionStatus(TransactionStatus.COMPLETED)
                                .build());
                System.out.println("Withdrawal successful");
            }
        }
    }

    public double formatToTwoDecimals(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }

    public void runMonthlyUpdateForAllAccounts() {
        for (BankAccount bankAccount : DB.bankAccounts) {
            bankAccount.runMonthlyUpdate();
        }
    }
}

//checked if account is paused for monthly billings
//checked if account has sufficient funds
//deducted monthly fee
//added transaction to transaction history
//    private void runMonthlyUpdate(BankAccount bankAccount) {
//        if (bankAccount.getPausedMonthlyBillingsUntil() != null && bankAccount.getPausedMonthlyBillingsUntil().isAfter(LocalDateTime.now())) {
//            return;
//        }
//        if (bankAccount.getAccountType() == AccountType.CHECKING) {
//            if (bankAccount.getBalanceAmount() < CheckingBankAccount.MONTHLY_FEE) {
//                System.out.println("Monthly fee of " + CheckingBankAccount.MONTHLY_FEE + " for checking account " + bankAccount.getAccountNumber() + "has not been deducted due to insufficient funds");
//                return;
//            }
//            bankAccount.setBalanceAmount(bankAccount.getBalanceAmount() - (CheckingBankAccount.MONTHLY_FEE));
//            Transaction bankServicesBillingTransaction = Transaction.builder()
//                    .amount(CheckingBankAccount.MONTHLY_FEE)
//                    .sourceAccount(bankAccount)
//                    .transactionTime(LocalDateTime.now())
//                    .transactionType(TransactionType.BANK_BILLING)
//                    .transactionStatus(TransactionStatus.COMPLETED)
//                    .build();
//            TransactionDB.transactions.add(bankServicesBillingTransaction);
//            System.out.println("Monthly fee of " + CheckingBankAccount.MONTHLY_FEE + " for checking account " + bankAccount.getAccountNumber() + "has been deducted");
//        } else if (bankAccount.getAccountType() == AccountType.SAVINGS) {
//            if (bankAccount.getBalanceAmount() < (bankAccount.getBalanceAmount() * SavingsBankAccount.INTEREST_RATE)) {
//                System.out.println("Monthly fee of " + (bankAccount.getBalanceAmount() - (bankAccount.getBalanceAmount() * SavingsBankAccount.INTEREST_RATE)) + " for saving account " + bankAccount.getAccountNumber() + "has not been deducted due to insufficient funds");
//                return;
//            }
//            //TODO e kom bo nihere set pastaj add transaction po e kom ndreq me tests
//            System.out.println("Monthly fee of " + formatToTwoDecimals(bankAccount.getBalanceAmount() * SavingsBankAccount.INTEREST_RATE) + " for saving account " + bankAccount.getAccountNumber() + "has been deducted");
//            Transaction bankServicesBillingTransaction = Transaction.builder()
//                    .amount(bankAccount.getBalanceAmount() * SavingsBankAccount.INTEREST_RATE)
//                    .sourceAccount(bankAccount)
//                    .transactionTime(LocalDateTime.now())
//                    .transactionType(TransactionType.BANK_BILLING)
//                    .transactionStatus(TransactionStatus.COMPLETED)
//                    .build();
//            bankAccount.setBalanceAmount(bankAccount.getBalanceAmount() - (bankAccount.getBalanceAmount() * SavingsBankAccount.INTEREST_RATE));
//            TransactionDB.transactions.add(bankServicesBillingTransaction);
//        }
//    }
