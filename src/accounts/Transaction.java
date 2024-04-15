package accounts;

import enums.TransactionStatus;
import enums.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Transaction {

    private BankAccount sourceAccount; //here we get bankUser and accountType
    private BankAccount destinationAccount;
    private double amount; //amountBilled
    private TransactionType transactionType;
    private LocalDateTime transactionTime;
    private TransactionStatus transactionStatus;

    public String toString() {
        String print = "Transaction from account of: " + sourceAccount.getAccountHolder().getFirstName() + " " + sourceAccount.getAccountHolder().getLastName() +
                "\n     Transaction Type: " + transactionType;
        if (transactionType.equals(TransactionType.TRANSFER)) {
            print += "\n     Transaction to account of: " + destinationAccount.getAccountHolder().getFirstName();
        }
        print += "\n     Amount: " + amount +
                "\n     Transaction Time: " + transactionTime +
                "\n     Transaction Status: " + transactionStatus;
        return print;
    }

}
