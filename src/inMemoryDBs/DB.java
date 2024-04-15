package inMemoryDBs;

import accounts.BankAccount;
import accounts.Transaction;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import users.BankAccountHolder;
import users.BankUser;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DB {
    public static List<BankUser> bankUsers = new ArrayList<>();
    public static List<BankAccount> bankAccounts = new ArrayList<>();
    public static List<Transaction> transactions = new ArrayList<>();

    public static <T> List<T> fillData(String filePath, Class<T> clazz) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                    .create();
            FileReader reader = new FileReader(filePath);
            Type bankUsersType = TypeToken.getParameterized(ArrayList.class, clazz).getType();
            return gson.fromJson(reader, bankUsersType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String dateTimeString = json.getAsJsonPrimitive().getAsString();
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);
        }
    }

    public <T extends BankAccount> void fillBankAccounts(String filePath, Class<T> clazz) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                    .create();
            FileReader reader = new FileReader(filePath);
            Type bankAccountListType = TypeToken.getParameterized(ArrayList.class, clazz).getType();
            List<T> bankAccountsRead = gson.fromJson(reader, bankAccountListType);
            reader.close();
            bankAccounts.addAll(bankAccountsRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T extends Transaction> void fillTransactions(String filePath, Class<T> clazz) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                    .create();
            FileReader reader = new FileReader(filePath);
            Type transactionListType = TypeToken.getParameterized(ArrayList.class, clazz).getType();
            List<T> transactionsRead = gson.fromJson(reader, transactionListType);
            reader.close();
            transactions.addAll(transactionsRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static BankAccount getBankAccountByAccountNumber(String accountNumber) {
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getAccountNumber().equals(accountNumber)) {
                return bankAccount;
            }
        }
        return null;
    }

    public static BankAccount getBankAccountByAccountHolder(BankAccountHolder accountHolder) {
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getAccountHolder().equals(accountHolder)) {
                return bankAccount;
            }
        }
        return null;
    }

    public static Object getUserByFullName(String name, String surname) {
        for (BankUser bankUser : bankUsers) {
            if (bankUser.getFirstName().equals(name) && bankUser.getLastName().equals(surname)) {
                return bankUser;
            }
        }
        return null;
    }

}

//public static void fillBankAccountHolders() {
//    try {
//        Gson gson = new Gson();
//        FileReader reader = new FileReader("src/jsonFiles/bankAccountHolders.json");
//        Type bankUsersType = new TypeToken<ArrayList<BankAccountHolder>>(){}.getType();
//        List<BankAccountHolder> bankUsersRead = gson.fromJson(reader, bankUsersType);
//        reader.close();
//        bankUsers.addAll(bankUsersRead);
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}
//
//public static void fillBankEmployees() {
//    try {
//        Gson gson = new Gson();
//        FileReader reader = new FileReader("src/jsonFiles/bankEmployees.json");
//        Type bankUsersType = new TypeToken<ArrayList<BankEmployee>>() {
//        }.getType();
//        List<BankEmployee> bankUsersRead = gson.fromJson(reader, bankUsersType);
//        reader.close();
//        bankUsers.addAll(bankUsersRead);
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}
//
//public static void fillBankOwners() {
//    try {
//        Gson gson = new Gson();
//        FileReader reader = new FileReader("src/jsonFiles/bankOwners.json");
//        Type bankUsersType = new TypeToken<ArrayList<BankOwner>>() {
//        }.getType();
//        List<BankOwner> bankUsersRead = gson.fromJson(reader, bankUsersType);
//        reader.close();
//        bankUsers.addAll(bankUsersRead);
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}
