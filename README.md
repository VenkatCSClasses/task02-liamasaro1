# Bank Account Implementation - Task 02

## Overview
This implementation provides a complete BankAccount system in Java 17 based on the specifications in spec.md and all test cases in tests.yaml.

## Files

### 1. BankAccount.java
Main class implementing bank account functionality with the following features:

**Constructor:**
- `BankAccount(String email, double startingBalance)` - Creates account with validation

**Methods:**
- `getBalance()` - Returns current balance
- `getEmail()` - Returns account email
- `withdraw(double amount)` - Withdraws funds (allows 0 as no-op)
- `deposit(double amount)` - Deposits funds (0 not allowed)
- `transfer(BankAccount toAccount, double amount)` - Transfers between accounts (0 not allowed)

**Static Validation Methods:**
- `isEmailValid(String email)` - Validates email addresses
- `isAmountValid(double amount)` - Validates monetary amounts

### 2. InsufficientFundsException.java
Custom checked exception thrown when withdrawal/transfer exceeds available balance.

### 3. BankAccountTest.java
Comprehensive JUnit 4 test suite with all test cases from tests.yaml:
- 3 getBalance tests
- 6 withdraw tests
- 6 deposit tests
- 7 transfer tests
- 10 isEmailValid tests
- 3 constructor tests
- 9 isAmountValid tests

**Total: 44 test cases**

## Implementation Details

### Email Validation Rules
- Exactly one @ symbol required
- Non-empty prefix and domain
- Prefix cannot start with dot
- No consecutive dots
- Domain must contain at least one dot
- Domain cannot end with dot
- Prefix: alphanumeric, dots, underscores only (no hyphens)
- No whitespace anywhere
- Not null or empty

### Amount Validation Rules
- Must be positive (> 0)
- Maximum 2 decimal places
- Trailing zeros allowed (20.5700000 is valid)
- Uses epsilon comparison (0.0000001) for floating-point precision

### Special Behaviors
1. **withdraw(0)** - Allowed as no-op operation
2. **deposit(0)** - Throws IllegalArgumentException  
3. **transfer(0)** - Throws IllegalArgumentException
4. **Insufficient funds** - Throws IllegalArgumentException (per tests.yaml)
5. **Constructor with 0 balance** - Not allowed (must use isAmountValid)

### Exception Handling
- **IllegalArgumentException**: Invalid email, invalid amount, zero deposit/transfer, insufficient funds
- **InsufficientFundsException**: Currently not used (tests specify IllegalArgumentException for insufficient funds)

## Running the Tests

### Prerequisites
- Java 17 or later (Java 21 compatible)
- JUnit 4.13.2
- Hamcrest Core 1.3

### Compile
```bash
javac BankAccount.java InsufficientFundsException.java
javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar BankAccountTest.java
```

### Run Tests
```bash
java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore BankAccountTest
```

### With Maven
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
```

```bash
mvn test
```

## Test Coverage Summary

✅ All getBalance scenarios including edge case (balance of 0 after withdrawal)
✅ All withdrawal scenarios including zero withdrawal and exception cases
✅ All deposit scenarios with validation and edge cases
✅ All transfer operations with proper validation
✅ Comprehensive email validation (10 test cases)
✅ Constructor validation for email and balance
✅ Complete amount validation (9 test cases)

## Notes on tests.yaml Interpretation

1. **getBalance test (line 9-10)**: Interpreted as testing balance after withdrawal to zero
2. **withdraw(0)**: Allowed per test line 23-25
3. **Insufficient funds**: Throws IllegalArgumentException per tests (lines 33-34, 62)
4. **deposit/transfer(0)**: Throws IllegalArgumentException per tests (lines 49-51, 70-72)

## Code Quality
- ✅ Java 17 compatible
- ✅ Complete Javadoc documentation for all public methods
- ✅ Pure functions with no I/O or side effects
- ✅ All boundary cases handled
- ✅ Proper exception handling throughout
- ✅ Floating-point precision handled with epsilon comparison

## Author
Bank Account System v1.0
Generated from spec.md and tests.yaml specifications
