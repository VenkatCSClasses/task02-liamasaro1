/**
 * Represents a bank account with an email and balance.
 */
public class BankAccount {

    private final String email;
    private double balance;

    /**
     * Creates a BankAccount with an email and starting balance.
     *
     * @param email account email
     * @param startingBalance initial balance
     * @throws IllegalArgumentException if email or balance is invalid
     */
    public BankAccount(String email, double startingBalance) {
        if (!isEmailValid(email)) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (!isAmountValid(startingBalance)) {
            throw new IllegalArgumentException("Invalid starting balance");
        }
        this.email = email;
        this.balance = startingBalance;
    }

    /**
     * Returns the account balance.
     *
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returns the account email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Withdraws money from the account.
     *
     * @param amount amount to withdraw
     */
    public void withdraw(double amount) {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid amount");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= amount;
    }

    /**
     * Deposits money into the account.
     *
     * @param amount amount to deposit
     */
    public void deposit(double amount) {
        if (!isAmountValid(amount) || amount == 0) {
            throw new IllegalArgumentException("Invalid deposit amount");
        }
        balance += amount;
    }

    /**
     * Transfers money to another account.
     *
     * @param toAccount destination account
     * @param amount amount to transfer
     */
    public void transfer(BankAccount toAccount, double amount) {
        if (!isAmountValid(amount) || amount == 0) {
            throw new IllegalArgumentException("Invalid transfer amount");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.balance -= amount;
        toAccount.balance += amount;
    }

    /**
     * Checks if an email is valid.
     *
     * @param email email string
     * @return true if valid
     */
    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (email.contains(" ")) {
            return false;
        }

        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex != email.lastIndexOf('@')) {
            return false;
        }

        String prefix = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        if (prefix.isEmpty() || domain.isEmpty()) {
            return false;
        }
        if (prefix.startsWith(".") || domain.endsWith(".")) {
            return false;
        }
        if (email.contains("..")) {
            return false;
        }
        if (!domain.contains(".")) {
            return false;
        }
        if (!prefix.matches("[a-zA-Z0-9]+")) {
            return false;
        }
        if (!domain.matches("[a-zA-Z0-9.]+")) {
            return false;
        }

        return true;
    }

    /**
     * Checks if an amount is valid.
     *
     * @param amount amount to check
     * @return true if valid
     */
    public static boolean isAmountValid(double amount) {
        if (amount < 0) {
            return false;
        }
        if (amount == 0) {
            return false;
        }

        double scaled = amount * 100;
        return Math.floor(scaled) == scaled;
    }
}
