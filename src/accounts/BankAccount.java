package accounts;

import adapters.JsonSubtype;
import adapters.JsonType;
import enums.AccountType;
import lombok.Getter;
import lombok.Setter;
import users.BankAccountHolder;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonType(
        property = "accountType",
        subtypes = {
                @JsonSubtype(clazz = CheckingBankAccount.class, name = "CHECKING"),
                @JsonSubtype(clazz = SavingsBankAccount.class, name = "SAVINGS")
        }
)
public abstract class BankAccount {

    private String accountNumber;
    private BankAccountHolder accountHolder;
    private double balanceAmount;
    private AccountType accountType;
    private LocalDateTime pausedMonthlyBillingsUntil;

    public BankAccount(String accountNumber, BankAccountHolder accountHolder, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balanceAmount = 0.0;
        this.accountType = accountType;
    }

    public String toString() {
        return "\nAccount Number: " + accountNumber +
                "\nAccount Holder: " + accountHolder.getFirstName() + " " + accountHolder.getLastName() +
                "\nAccount Balance: " + balanceAmount;
    }

    public boolean equals(Object obj) {
        if (obj instanceof BankAccount bankAccount) {
            return this.accountNumber.equals(bankAccount.accountNumber);
        }
        return false;
    }

    public abstract void runMonthlyUpdate();
}
