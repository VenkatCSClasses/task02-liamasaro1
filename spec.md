# Bank Account Specification

## Overview
BankAccounts are objects represnting Bank Accounts. They have an email and an account balance as parameters. Bank Accounts have the ability to withdraw, deposit, and transfer within them.

## Design Principles
- Program using **Java 17**
- Pure functions with no I/O or side effects
- **Well Documented**: code should have thorough and user friendly readable documentation. Each method should have a valid Javadoc.
- Handles all boundary cases
- 

## Bank Account Class
### Constructor
#### BankAccount(String email, double startingBalance)
- Takes an email address and starting balance to create a BankAccount object
- Throws **IllegalArgumentException** if startingBalance is invalid based on the **static** isEmailValid() method
- Throws **IllegalArgumentException** if email address is invalid based on the **static** isAmountValid() method

### Methods
##
#### getBalance()
- Returns the current account balance: **double**
#### getEmail()
- Returns email address of the account: **String**
#### withdraw(double amount)
- If amount is valid, removes amount from remaining balance
- Throws **InsufficentFundsException** if amount is greater than remaining account balance
- Throws **IllegalArgumentException** if amount is negative or invalid
- **Void**
#### deposit(double amount)
- If amount is valid, increases account balance by amount
- Throws **IllegalArgumentException** if amount is negative or invalid
- **Void**
#### transfer(BankAccount toAccount, douuble amount)
- Transfers amount from this account to toAccount if amount is non-negative, valid, and smaller than or equal to balance
- Throws **InsufficientFundsException** if amount is greater than balance
- Throws **IllegalArgumentException** if amount is negative or invalid
- **Void**
#### isEmailValid(String email)
- Returns **true** if email is valid, **false** otherwise
- See tests for specifications on what makes an email valid
- Examples include, exactly 1 @ symbol, no consecutive, cannot be null/empty string, cannot be dot at end of domain, prefix cannot start with an invalid character, cannot have empty prefix, cannot have empty domain, cannot contain whitespace or begin with a dot
#### isAmountValid(double amount)
- Returns true if amount is non-negative and has at most two decimal places, false otherwise
- Allows for 2 decimal places with as many trailing zeros as possible