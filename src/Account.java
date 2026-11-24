public class Account {

    private String name;
    private String pin;
    private double balance = 0;
    private int wrongPinCount = 0;
    private boolean isLocked = false;

    public Account(String accountName, String pin) {
        name = accountName;
        this.pin = pin;
        if (pin == null) {
            this.pin = "1234";
        }
        if (pin != null && pin.length() != 4) {
            this.pin = "1234";
        }
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean withdraw(double amount, String enteredPin) {
        if (isLocked == true) {
            return false;
        }
        if (pin.equals(enteredPin) == false) {
            wrongPinCount = wrongPinCount + 1;
            if (wrongPinCount >= 3) {
                isLocked = true;
            }
            return false;
        }
        if (amount <= 0) {
            return false;
        }
        if (amount > balance) {
            return false;
        }
        balance = balance - amount;
        return true;
    }

    public boolean changePin(String oldPin, String newPin) {
        if (isLocked == true) {
            return false;
        }
        if (pin.equals(oldPin) == false) {
            return false;
        }
        if (newPin == null) {
            return false;
        }
        if (newPin.length() != 4) {
            return false;
        }
        pin = newPin;
        return true;
    }

    public double getBalance(String enteredPin) {
        if (isLocked == true) {
            return -1;
        }
        if (pin.equals(enteredPin) == false) {
            wrongPinCount = wrongPinCount + 1;
            if (wrongPinCount >= 3) {
                isLocked = true;
            }
            return -1;
        }
        wrongPinCount = 0;
        return balance;
    }

    public boolean isLocked() {

        return isLocked;
    }

    public String getName() {

        return name;
    }
}