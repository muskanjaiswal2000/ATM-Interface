import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

  class User {
    private String userId;
    private String pin;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class ATM {
    private double balance;
    private Map<String, User> users;
    private Map<String, Transaction> transactionHistory;

    public ATM() {
        this.balance = 1000.0; 
        this.users = new HashMap<>();
        this.transactionHistory = new HashMap<>();
    }

    public void addUser(String userId, String pin) {
        users.put(userId, new User(userId, pin));
    }

    public boolean authenticateUser(String userId, String pin) {
        User user = users.get(userId);
        return user != null && user.getPin().equals(pin);
    }

    public void displayMenu() {
        System.out.println("1. Transactions History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
    }

    public void performTransaction(String userId, int choice, double amount) {
        switch (choice) {
            case 1:
                displayTransactionHistory(userId);
                break;
            case 2:
                withdraw(userId, amount);
                break;
            case 3:
                deposit(userId, amount);
                break;
            case 4:
                transfer(userId, amount);
                break;
            case 5:
                System.out.println("Exiting the ATM. Thank you for using!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void displayTransactionHistory(String userId) {
        System.out.println("Transaction History for User: " + userId);
        for (Transaction transaction : transactionHistory.values()) {
            System.out.println("Type: " + transaction.getType() + ", Amount: " + transaction.getAmount());
        }
    }

    private void withdraw(String userId, double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            recordTransaction(userId, "Withdraw", amount);
            System.out.println("Withdrawal successful. Remaining balance: " + balance);
        }
    }

    private void deposit(String userId, double amount) {
        balance += amount;
        recordTransaction(userId, "Deposit", amount);
        System.out.println("Deposit successful. New balance: " + balance);
    }

    private void transfer(String userId, double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance for transfer.");
        } else {
            balance -= amount;
            recordTransaction(userId, "Transfer", amount);
            System.out.println("Transfer successful. Remaining balance: " + balance);
        }
    }

    private void recordTransaction(String userId, String type, double amount) {
        Transaction transaction = new Transaction(type, amount);
        transactionHistory.put(userId, transaction);
    }
}

 class ATMApplication {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.addUser("Muskan", "1111");
        
	Scanner scanner = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        String userId = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        if (atm.authenticateUser(userId, pin)) {
            while (true) {
                atm.displayMenu();
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice >= 1 && choice <= 4) {
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    atm.performTransaction(userId, choice, amount);
                } else if (choice == 5) {
                    atm.performTransaction(userId, choice, 0); 
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Authentication failed. Exiting...");
	}
    }
}
