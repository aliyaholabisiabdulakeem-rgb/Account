//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {
    @Test
    public void testNewAccountBalanceIsZero() {
        Account account = new Account("Aliyah", "1234");
        assertEquals(0, account.getBalance("1234"));
    }

    @Test
    public void testAccountNotLockedAtStart() {
        Account account = new Account("Aliyah", "1234");
        assertEquals(false, account.isLocked());
    }

    @Test
    public void testDepositWorks() {
        Account account = new Account("Aliyah", "1234");
        assertTrue(account.deposit(10000));
        assertEquals(10000, account.getBalance("1234"));
    }

    @Test
    public void testDepositTwiceAddsUp() {
        Account account = new Account("Aliyah", "1234");
        account.deposit(5000);
        account.deposit(3000);
        assertEquals(8000, account.getBalance("1234"));
    }

    @Test
    public void testCannotDepositZero() {
        Account account = new Account("Aliyah", "1234");
        assertEquals(false, account.deposit(0));
        assertEquals(0, account.getBalance("1234"));
    }

    @Test
    public void testCannotDepositNegative() {
        Account account = new Account("Aliyah", "1234");
        assertEquals(false, account.deposit(-500));
        assertEquals(0, account.getBalance("1234"));
    }

    @Test
    public void testWithdrawWorksWithCorrectPin() {
        Account account = new Account("Aliyah", "1234");
        account.deposit(10000);
        assertTrue(account.withdraw(4000, "1234"));
        assertEquals(6000, account.getBalance("1234"));
    }

    @Test
    public void testCannotWithdrawWithWrongPin() {
        Account account = new Account("Aliyah", "1234");
        account.deposit(10000);
        assertEquals(false, account.withdraw(1000, "9999"));
        assertEquals(10000, account.getBalance("1234"));
    }

    @Test
    public void testCannotWithdrawMoreThanBalance() {
        Account account = new Account("Aliyah", "1234");
        account.deposit(5000);
        assertEquals(false, account.withdraw(10000, "1234"));
        assertEquals(5000, account.getBalance("1234"));
    }

    @Test
    public void testCannotWithdrawZero() {
        Account account = new Account("Aliyah", "1234");
        account.deposit(5000);
        assertEquals(false, account.withdraw(0, "1234"));
    }

    @Test
    public void testCannotWithdrawNegative() {
        Account account = new Account("Aliyah", "1234");
        account.deposit(5000);
        assertEquals(false, account.withdraw(-500, "1234"));
    }

    @Test
    public void testCannotCheckBalanceWithWrongPin() {
        Account account = new Account("Aliyah", "1234");
        assertEquals(-1, account.getBalance("9999"));
    }

    @Test
    public void testChangePinWorks() {
        Account account = new Account("Aliyah", "1234");
        assertTrue(account.changePin("1234", "5678"));
        assertEquals(0, account.getBalance("5678"));
        assertEquals(-1, account.getBalance("1234"));
    }

    @Test
    public void testCannotChangePinWithWrongOldPin() {
        Account account = new Account("Aliyah", "1234");
        assertEquals(false, account.changePin("9999", "5678"));
    }

    @Test
    public void testCannotChangePinToWrongLength() {
        Account account = new Account("Aliyah", "1234");
        assertEquals(false, account.changePin("1234", "12"));
    }

    @Test
    public void testOneWrongPinDoesNotLock() {
        Account account = new Account("Aliyah", "1234");
        assertEquals(-1, account.getBalance("9999"));
        assertEquals(false, account.isLocked());
        assertEquals(0, account.getBalance("1234"));
    }

    @Test
    public void testAccountLocksAfter3WrongPins() {
        Account account = new Account("Aliyah", "1234");
        account.getBalance("1111");
        account.getBalance("2222");
        account.getBalance("3333");
        assertTrue(account.isLocked());
    }

    @Test
    public void testLockedAccountCannotWithdrawEvenWithCorrectPin() {
        Account account = new Account("Aliyah", "1234");
        account.deposit(5000);
        account.getBalance("1111");
        account.getBalance("2222");
        account.getBalance("3333");

        assertTrue(account.isLocked());
        assertEquals(false, account.withdraw(1000, "1234"));
        assertEquals(-1, account.getBalance("1234"));
    }
}