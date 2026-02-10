import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive test suite for the BankAccount class.
 * Tests all functionality including constructors, getters, withdrawals,
 * deposits, transfers, and validation methods.
 * 
 * <p>All test cases are derived from the tests.yaml specification.
 * 
 * @author Bank Account System
 * @version 1.0
 */
public class BankAccountTest {
    
    private static final double DELTA = 0.001;
    
    // ==================== getBalance Tests ====================
    
    @Test
    public void testGetBalance_MiddleCase() {
        BankAccount account = new BankAccount("test@test.com", 200.0);
        assertEquals(200.0, account.getBalance(), DELTA);
    }
    
    @Test
    public void testGetBalance_EdgeCase() throws InsufficientFundsException {
        BankAccount account = new BankAccount("test@test.com", 100.0);
        account.withdraw(100.0);
        assertEquals(0.0, account.getBalance(), DELTA);
    }
    
    @Test
    public void testGetBalance_SmallDecimal() {
        BankAccount account = new BankAccount("test@test.com", 0.30);
        assertEquals(0.30, account.getBalance(), DELTA);
    }
    
    // ==================== withdraw Tests ====================
    
    @Test
    public void testWithdraw_MiddleCase() throws InsufficientFundsException {
        BankAccount account = new BankAccount("test@test.com", 200.0);
        account.withdraw(100.0);
        assertEquals(100.0, account.getBalance(), DELTA);
    }
    
    @Test
    public void testWithdraw_EntireBalance() throws InsufficientFundsException {
        BankAccount account = new BankAccount("test@test.com", 100.0);
        account.withdraw(100.0);
        assertEquals(0.0, account.getBalance(), DELTA);
    }
    
    @Test
    public void testWithdraw_Zero() throws InsufficientFundsException {
        BankAccount account = new BankAccount("test@test.com", 100.0);
        account.withdraw(0.0);
        assertEquals(100.0, account.getBalance(), DELTA);
    }
    
    @Test
    public void testWithdraw_SmallDecimal() throws InsufficientFundsException {
        BankAccount account = new BankAccount("test@test.com", 100.0);
        account.withdraw(0.30);
        assertEquals(99.70, account.getBalance(), DELTA);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWithdraw_NegativeAmount() throws InsufficientFundsException {
        BankAccount account = new BankAccount("test@test.com", 100.0);
        account.withdraw(-50.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWithdraw_AmountGreaterThanBalance() throws InsufficientFundsException {
        BankAccount account = new BankAccount("test@test.com", 100.0);
        account.withdraw(150.0);
    }
    
    // ==================== deposit Tests ====================
    
    @Test
    public void testDeposit_MiddleCase() {
        BankAccount account = new BankAccount("test@test.com", 100.0);
        account.deposit(100.0);
        assertEquals(200.0, account.getBalance(), DELTA);
    }
    
    @Test
    public void testDeposit_SmallAmount() {
        BankAccount account = new BankAccount("test@test.com", 100.0);
        account.deposit(0.30);
        assertEquals(100.30, account.getBalance(), DELTA);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDeposit_MoreThan2DecimalPlaces() {
        BankAccount account = new BankAccount("test@test.com", 100.0);
        account.deposit(0.123);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDeposit_NegativeAmount() {
        BankAccount account = new BankAccount("test@test.com", 100.0);
        account.deposit(-50.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDeposit_ZeroAmount() {
        BankAccount account = new BankAccount("test@test.com", 100.0);
        account.deposit(0.0);
    }
    
    @Test
    public void testDeposit_LargeAmount() {
        BankAccount account = new BankAccount("test@test.com", 100.0);
        account.deposit(1000000.0);
        assertEquals(1000100.0, account.getBalance(), DELTA);
    }
    
    // ==================== transfer Tests ====================
    
    @Test
    public void testTransfer_MiddleCase() throws InsufficientFundsException {
        BankAccount acc1 = new BankAccount("acc1@test.com", 100.0);
        BankAccount acc2 = new BankAccount("acc2@test.com", 100.0);
        acc1.transfer(acc2, 50.0);
        assertEquals(50.0, acc1.getBalance(), DELTA);
        assertEquals(150.0, acc2.getBalance(), DELTA);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testTransfer_InsufficientFunds() throws InsufficientFundsException {
        BankAccount acc1 = new BankAccount("acc1@test.com", 150.0);
        BankAccount acc2 = new BankAccount("acc2@test.com", 150.0);
        acc1.transfer(acc2, 200.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testTransfer_NegativeAmount() throws InsufficientFundsException {
        BankAccount acc1 = new BankAccount("acc1@test.com", 150.0);
        BankAccount acc2 = new BankAccount("acc2@test.com", 150.0);
        acc1.transfer(acc2, -50.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testTransfer_MoreThan2DecimalPlaces() throws InsufficientFundsException {
        BankAccount acc1 = new BankAccount("acc1@test.com", 150.0);
        BankAccount acc2 = new BankAccount("acc2@test.com", 150.0);
        acc1.transfer(acc2, 0.123);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testTransfer_ZeroAmount() throws InsufficientFundsException {
        BankAccount acc1 = new BankAccount("acc1@test.com", 150.0);
        BankAccount acc2 = new BankAccount("acc2@test.com", 150.0);
        acc1.transfer(acc2, 0.0);
    }
    
    @Test
    public void testTransfer_SmallDecimal() throws InsufficientFundsException {
        BankAccount acc1 = new BankAccount("acc1@test.com", 50.0);
        BankAccount acc2 = new BankAccount("acc2@test.com", 150.0);
        acc1.transfer(acc2, 0.30);
        assertEquals(49.70, acc1.getBalance(), DELTA);
        assertEquals(150.30, acc2.getBalance(), DELTA);
    }
    
    @Test
    public void testTransfer_EntireBalance() throws InsufficientFundsException {
        BankAccount acc1 = new BankAccount("acc1@test.com", 50.0);
        BankAccount acc2 = new BankAccount("acc2@test.com", 150.0);
        acc1.transfer(acc2, 50.0);
        assertEquals(0.0, acc1.getBalance(), DELTA);
        assertEquals(200.0, acc2.getBalance(), DELTA);
    }
    
    // ==================== isEmailValid Tests ====================
    
    @Test
    public void testIsEmailValid_MiddleCase() {
        assertTrue(BankAccount.isEmailValid("a@b.com"));
    }
    
    @Test
    public void testIsEmailValid_NullInput() {
        assertFalse(BankAccount.isEmailValid(null));
    }
    
    @Test
    public void testIsEmailValid_NoAtSymbol() {
        assertFalse(BankAccount.isEmailValid("abc.com"));
    }
    
    @Test
    public void testIsEmailValid_NoDomain() {
        assertFalse(BankAccount.isEmailValid("a@bcdefg"));
    }
    
    @Test
    public void testIsEmailValid_MultipleAtSymbols() {
        assertFalse(BankAccount.isEmailValid("a@@b.com"));
    }
    
    @Test
    public void testIsEmailValid_ConsecutiveDots() {
        assertFalse(BankAccount.isEmailValid("a@b..com"));
    }
    
    @Test
    public void testIsEmailValid_InvalidCharacters() {
        assertFalse(BankAccount.isEmailValid("a-b@c.com"));
    }
    
    @Test
    public void testIsEmailValid_MissingPrefix() {
        assertFalse(BankAccount.isEmailValid("@b.com"));
    }
    
    @Test
    public void testIsEmailValid_PrefixStartsWithDot() {
        assertFalse(BankAccount.isEmailValid(".abc@bc.com"));
    }
    
    @Test
    public void testIsEmailValid_ContainsWhitespace() {
        assertFalse(BankAccount.isEmailValid("a @b.com"));
    }
    
    // ==================== Constructor Tests ====================
    
    @Test
    public void testConstructor_MiddleCase() {
        BankAccount account = new BankAccount("abc@abc.com", 100.0);
        assertEquals("abc@abc.com", account.getEmail());
        assertEquals(100.0, account.getBalance(), DELTA);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_InvalidEmail() {
        new BankAccount("abc.com", 100.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_NegativeBalance() {
        new BankAccount("abc@abc.com", -100.0);
    }
    
    // ==================== isAmountValid Tests ====================
    
    @Test
    public void testIsAmountValid_ZeroDecimalPlaces() {
        assertTrue(BankAccount.isAmountValid(100.0));
    }
    
    @Test
    public void testIsAmountValid_TwoDecimalPlaces() {
        assertTrue(BankAccount.isAmountValid(3000.87));
    }
    
    @Test
    public void testIsAmountValid_MoreThan2DecimalPlaces() {
        assertFalse(BankAccount.isAmountValid(100.1200007));
    }
    
    @Test
    public void testIsAmountValid_NegativeAmount() {
        assertFalse(BankAccount.isAmountValid(-50.0));
    }
    
    @Test
    public void testIsAmountValid_ZeroAmount() {
        assertFalse(BankAccount.isAmountValid(0.0));
    }
    
    @Test
    public void testIsAmountValid_OneDecimalPlace() {
        assertTrue(BankAccount.isAmountValid(300.3));
    }
    
    @Test
    public void testIsAmountValid_SmallDecimal() {
        assertTrue(BankAccount.isAmountValid(0.30));
    }
    
    @Test
    public void testIsAmountValid_LargeAmount() {
        assertTrue(BankAccount.isAmountValid(1000000.0));
    }
    
    @Test
    public void testIsAmountValid_TrailingZeros() {
        assertTrue(BankAccount.isAmountValid(20.57));
    }
}
