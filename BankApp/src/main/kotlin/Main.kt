package org.example

import org.example.bank.Bank

fun main() {
    val bank = Bank("Brixton Bank")
    println("Welcome to " + bank.name + "!")
    println("Please create an account or login to your account to use this application.")
    println("This application is stored in memory only and will delete all information once the application is closed, you will need to create a new account every time you restart.")
    println("List of commands:")
    println("- New Account: NewAccount [FirstName] [LastName]`")
    println("- Deposit: `Deposit [AccountNumber] [SortCode] [Amount]`")
    println("- Withdraw: `Withdraw [AccountNumber] [SortCode] [Amount]`")
    println("- Balance: `Balance [AccountNumber] [SortCode]`")
    println("- Quit: `Quit`")

    while (true) {
        print("> ")
        val input = readlnOrNull() ?: break
        val commandValues = input.trim().split("\\s+".toRegex())

        val namePattern = "^[A-Z][a-zA-Z]+$".toRegex()

        if (commandValues.isEmpty()) continue

        try {
            when (commandValues[0].lowercase()) {
                "newaccount" -> {
                    require(commandValues.size == 3) {
                        "New Account: `NewAccount [FirstName] [LastName]`"
                    }
                    val firstName = commandValues[1]
                    val lastName = commandValues[2]

                    require(listOf(firstName, lastName).all { it.matches(namePattern) }) {
                        "First and last names must start with a capital letter and contain only letters"
                    }

                    val account = bank.createAccount(firstName, lastName)

                    require(account.accountNumber.isNotBlank() && account.sortCode.isNotBlank() ) { "Could not create account. Please try again." }

                    println("Account created successfully!")
                    println("Account Number: ${account.accountNumber}")
                    println("Sort Code: ${account.sortCode}")
                }

                "deposit" -> {
                    require(commandValues.size == 4) {
                        "Deposit: `Deposit [AccountNumber] [SortCode] [Amount]`"
                    }

                    val accountNumber = commandValues[1]
                    val sortCode = commandValues[2]
                    val amountDeposit = commandValues[3]

                    require(ValidationCheck.checkValid(accountNumber, sortCode)) {"Credentials are not valid."}

                    bank.deposit(accountNumber, sortCode.replace("-", ""), amountDeposit.toDouble())
                }

                "withdraw" -> {
                    require(commandValues.size == 4) {
                        "Withdraw: `Withdraw [AccountNumber] [SortCode] [Amount]`"
                    }

                    val accountNumber = commandValues[1]
                    val sortCode = commandValues[2]
                    val amountWithdrawn = commandValues[3]

                    require(ValidationCheck.checkValid(accountNumber, sortCode)) {"Credentials are not valid."}

                    bank.withdraw(accountNumber, sortCode.replace("-", ""), amountWithdrawn.toDouble())
                }

                "balance" -> {
                    require(commandValues.size == 3) {
                        "Balance: `Balance [AccountNumber] [SortCode]`"
                    }

                    val accountNumber = commandValues[1]
                    val sortCode = commandValues[2]

                    require(ValidationCheck.checkValid(accountNumber, sortCode)) {"Credentials are not valid."}

                    bank.getBalance(accountNumber, sortCode.replace("-", ""))
                }

                "quit" -> {
                    println("Bank Closed.")
                        println("Thank you for using this service! See you again soon.")
                    return
                }

                else -> {
                    println("Unknown command: ${commandValues[0]}")
                    println("Please enter a valid command or type `Quit` to exit.")
                }

            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}