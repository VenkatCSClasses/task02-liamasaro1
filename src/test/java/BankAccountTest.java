import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount acc = new BankAccount("a@b.com", 200);
        assertEquals(200, acc.getBalance());
    }

    @Test
    void withdrawTest() {
        BankAccount acc = new BankAccount("a@b.com", 100);
        acc.withdraw(30);
        assertEquals(70, acc.getBalance());
    }

    @Test
    void withdrawEntireBalance() {
        BankAccount acc = new BankAccount("a@b.com", 100);
        acc.withdraw(100);
        assertEquals(0, acc.getBalance());
    }

    @Test
    void withdrawNegativeThrows() {
        BankAccount acc = new BankAccount("a@b.com", 100);
        assertThrows(IllegalArgumentException.class, () -> acc.withdraw(-50));
    }

    @Test
    void withdrawTooMuchThrows() {
        BankAccount acc = new BankAccount("a@b.com", 100);
        assertThrows(InsufficientFundsException.class, () -> acc.withdraw(150));
    }

    @Test
    void depositTest() {
        BankAccount acc = new BankAccount("a@b.com", 100);
        acc.deposit(100);
        assertEquals(200, acc.getBalance());
    }

    @Test
    void depositInvalidThrows() {
        BankAccount acc = new BankAccount("a@b.com", 100);
        assertThrows(IllegalArgumentException.class, () -> acc.deposit(0));
    }

    @Test
    void transferTest() {
        BankAccount a = new BankAccount("a@b.com", 150);
        BankAccount b = new BankAccount("c@d.com", 150);
        a.transfer(b, 50);
        assertEquals(100, a.getBalance());
        assertEquals(200, b.getBalance());
    }

    @Test
    void transferTooMuchThrows() {
        BankAccount a = new BankAccount("a@b.com", 150);
        BankAccount b = new BankAccount("c@d.com", 150);
        assertThrows(InsufficientFundsException.class, () -> a.transfer(b, 200));
    }

    @Test
    void isEmailValidTest() {
        assertTrue(BankAccount.isEmailValid("a@b.com"));
        assertFalse(BankAccount.isEmailValid("abc.com"));
        assertFalse(BankAccount.isEmailValid("a@@b.com"));
        assertFalse(BankAccount.isEmailValid(".abc@b.com"));
        assertFalse(BankAccount.isEmailValid("a @b.com"));
    }

    @Test
    void constructorInvalidEmailThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new BankAccount("abc.com", 100));
    }

    @Test
    void isAmountValidTest() {
        assertTrue(BankAccount.isAmountValid(100));
        assertTrue(BankAccount.isAmountValid(300.30));
        assertFalse(BankAccount.isAmountValid(100.123));
        assertFalse(BankAccount.isAmountValid(-50));
        assertFalse(BankAccount.isAmountValid(0));
    }
}
