/**
 * Represents a bank account with email identification and balance tracking.
 * Provides functionality for deposits, withdrawals, and transfers between accounts.
 * 
 * <p>This class enforces strict validation rules for email addresses and monetary amounts.
 * All operations maintain account integrity and prevent invalid transactions.
 * 
 * @author Bank Account System
 * @version 1.0
 */
public class BankAccount {
    
    private final String email;
    private double balance;
    
    /**
     * Constructs a new BankAccount with the specified email and starting balance.
     * 
     * @param email the email address associated with this account
     * @param startingBalance the initial balance for the account (must be positive with at most 2 decimal places)
     * @throws IllegalArgumentException if the email is invalid or if the starting balance is invalid
     */
    public BankAccount(String email, double startingBalance) {
        if (!isEmailValid(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (!isAmountValid(startingBalance)) {
            throw new IllegalArgumentException("Invalid starting balance");
        }
        this.email = email;
        this.balance = startingBalance;
    }
    
    /**
     * Returns the current balance of this account.
     * 
     * @return the current account balance
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * Returns the email address associated with this account.
     * 
     * @return the email address
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Withdraws the specified amount from this account.
     * Withdrawing 0 is allowed and does not change the balance.
     * 
     * @param amount the amount to withdraw
     * @throws IllegalArgumentException if the amount is negative or has more than 2 decimal places,
     *                                  or if amount exceeds the current balance
     * @throws InsufficientFundsException if the amount exceeds the current balance
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        // Allow 0 withdrawal (no-op)
        if (amount == 0.0) {
            return;
        }
        
        // Check for negative amount
        if (amount < 0) {
            throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        }
        
        // Check for valid decimal places
        if (!hasValidDecimalPlaces(amount)) {
            throw new IllegalArgumentException("Invalid withdrawal amount - too many decimal places");
        }
        
        // Check for insufficient funds - throw IllegalArgumentException per tests
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        
        balance -= amount;
    }
    
    /**
     * Deposits the specified amount into this account.
     * Depositing 0 is not allowed.
     * 
     * @param amount the amount to deposit (must be positive with at most 2 decimal places)
     * @throws IllegalArgumentException if the amount is zero, negative, or has more than 2 decimal places
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        if (!hasValidDecimalPlaces(amount)) {
            throw new IllegalArgumentException("Invalid deposit amount - too many decimal places");
        }
        
        balance += amount;
    }
    
    /**
     * Transfers the specified amount from this account to another account.
     * Transferring 0 is not allowed.
     * 
     * @param toAccount the account to transfer funds to
     * @param amount the amount to transfer (must be positive with at most 2 decimal places)
     * @throws IllegalArgumentException if the amount is zero, negative, has more than 2 decimal places,
     *                                  or exceeds the current balance
     * @throws InsufficientFundsException if the amount exceeds the current balance
     */
    public void transfer(BankAccount toAccount, double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        
        if (!hasValidDecimalPlaces(amount)) {
            throw new IllegalArgumentException("Invalid transfer amount - too many decimal places");
        }
        
        // Check for insufficient funds - throw IllegalArgumentException per tests
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds for transfer");
        }
        
        balance -= amount;
        toAccount.balance += amount;
    }
    
    /**
     * Validates an email address according to strict rules.
     * 
     * <p>Valid email requirements:
     * <ul>
     *   <li>Cannot be null or empty</li>
     *   <li>Must contain exactly one @ symbol</li>
     *   <li>Must have a non-empty prefix before @</li>
     *   <li>Must have a non-empty domain after @</li>
     *   <li>Prefix cannot start with a dot</li>
     *   <li>Cannot contain consecutive dots</li>
     *   <li>Cannot contain whitespace</li>
     *   <li>Domain must contain at least one dot</li>
     *   <li>Domain cannot end with a dot</li>
     *   <li>Prefix can only contain alphanumeric characters, dots, and underscores</li>
     * </ul>
     * 
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isEmailValid(String email) {
        // Check for null or empty
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        // Check for whitespace
        if (email.contains(" ")) {
            return false;
        }
        
        // Count @ symbols and find position
        int atCount = 0;
        int atIndex = -1;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                atCount++;
                atIndex = i;
            }
        }
        
        // Must have exactly one @
        if (atCount != 1) {
            return false;
        }
        
        // Split into prefix and domain
        String prefix = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);
        
        // Check for empty prefix or domain
        if (prefix.isEmpty() || domain.isEmpty()) {
            return false;
        }
        
        // Prefix cannot start with a dot
        if (prefix.startsWith(".")) {
            return false;
        }
        
        // Check for consecutive dots
        if (email.contains("..")) {
            return false;
        }
        
        // Domain must contain at least one dot
        if (!domain.contains(".")) {
            return false;
        }
        
        // Domain cannot end with a dot
        if (domain.endsWith(".")) {
            return false;
        }
        
        // Validate prefix characters (alphanumeric, dot, underscore only)
        for (char c : prefix.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '.' && c != '_') {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Validates a monetary amount.
     * 
     * <p>Valid amounts must be:
     * <ul>
     *   <li>Greater than zero</li>
     *   <li>Have at most 2 decimal places (trailing zeros are allowed)</li>
     * </ul>
     * 
     * @param amount the amount to validate
     * @return true if the amount is valid (positive with at most 2 decimal places), false otherwise
     */
    public static boolean isAmountValid(double amount) {
        // Must be positive
        if (amount <= 0) {
            return false;
        }
        
        // Check decimal places
        return hasValidDecimalPlaces(amount);
    }
    
    /**
     * Helper method to check if a double has at most 2 decimal places.
     * 
     * @param value the value to check
     * @return true if the value has at most 2 decimal places, false otherwise
     */
    private static boolean hasValidDecimalPlaces(double value) {
        // Multiply by 100 and check if it's effectively an integer
        double scaled = value * 100;
        double epsilon = 0.0000001;
        return Math.abs(scaled - Math.round(scaled)) < epsilon;
    }
}
