//package inMemoryDBs;
//
//import accounts.Transaction;
//import com.google.gson.*;
//import com.google.gson.reflect.TypeToken;
//import enums.TransactionType;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TransactionDB {
//    public static List<Transaction> transactions = new ArrayList<>();
//
//    public static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
//        @Override
//        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//            String dateTimeString = json.getAsJsonPrimitive().getAsString();
//            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);
//        }
//    }
//
//    public static void fillTransactions() {
//        try {
//            // Create a Gson instance with custom deserializer for LocalDateTime
//            Gson gson = new GsonBuilder()
//                    .registerTypeAdapter(LocalDateTime.class, new TransactionDB.LocalDateTimeDeserializer())
//                    .create();
//
//            // Read the JSON array from the file into a FileReader
//            FileReader reader = new FileReader("src/jsonFiles/transactions.json");
//
//            // Define the type of the collection
//            Type bankAccountListType = new TypeToken<ArrayList<Transaction>>() {
//            }.getType();
//
//            // Parse the JSON array into a list of BankAccount objects
//            List<Transaction> bankAccountsRead = gson.fromJson(reader, bankAccountListType);
//
//            // Close the reader
//            reader.close();
//            transactions.addAll(bankAccountsRead);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        transactions.forEach(transaction -> {
//            System.out.println("Transaction from account of: " + transaction.getSourceAccount().getAccountHolder().getFirstName() + " " +
//                    transaction.getSourceAccount().getAccountHolder().getLastName() +
//                    "\n     Transaction Type: " + transaction.getTransactionType());
//            if (transaction.getTransactionType().equals(TransactionType.TRANSFER)) {
//                System.out.println("\n     Transaction to account of: " + transaction.getDestinationAccount().getAccountHolder().getFirstName());
//            }
//            System.out.println("\n     Amount: " + transaction.getAmount() +
//                    "\n     Transaction Time: " + transaction.getTransactionTime() +
//                    "\n     Transaction Status: " + transaction.getTransactionStatus());
//        });
//    }
//}
