# Brixton Bank CLI Application

## Overview
This is a simple text-based command line bank account program implemented in Kotlin. The application allows the user to:

1. Create a new bank account for a customer.
2. Deposit and withdraw cash to/from an account.
3. Display the balance of an account.
4. Quit the program.

All data is stored in-memory only, so it will be lost when the program exits.

## Features
- Create accounts with a first and last name.
- Deposit and withdraw funds with validation.
- Display account balances.
- Input validation and error handling to prevent crashes.

## Commands
| Command                         | Description                                | Usage                              |
|--------------------------------|--------------------------------------------|-----------------------------------|
| `NewAccount [FirstName] [LastName]` | Creates a new account                       | `NewAccount John Doe`              |
| `Deposit [AccountNumber] [SortCode] [Amount]` | Deposits amount into specified account    | `Deposit 80000000 40-00-00 50.00` |
| `Withdraw [AccountNumber] [SortCode] [Amount]` | Withdraws amount from specified account   | `Withdraw 80000000 40-00-00 20.00`|
| `Balance [AccountNumber] [SortCode]` | Shows current account balance               | `Balance 80000000 40-00-00`        |
| `Quit`                          | Exits the program                          | `Quit`                            |

## Usage

Compile the application:

```bash
kotlinc src/main/kotlin -include-runtime -d BankApp.jar
```


Run the application:

```bash
java -jar BankApp.jar 
```