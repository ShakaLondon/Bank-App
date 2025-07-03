package org.example.bank

import org.example.bankAccount.BankAccount

class Bank (val name: String){

    private val accounts = mutableMapOf<String, BankAccount>()
    private var newAccountNumber = 80000000
    private var newSortCodeNumber = 0

    private fun sortCodeGenerator(n: Int): String {
        val prefix = "40"
        val part1 = String.format("%02d", (n / 100) % 100)
        val part2 = String.format("%02d", n % 100)
        return "$prefix-$part1-$part2"
    }

    fun createAccount(firstName: String, lastName: String): BankAccount {
        val sortCode = sortCodeGenerator(newSortCodeNumber)
        val newAccount = BankAccount(newAccountNumber.toString(), sortCode, firstName, lastName)

        accounts[newAccount.accountNumber] = newAccount
        newAccountNumber++
        newSortCodeNumber++
        return newAccount
    }

    fun deposit (accountNumber: String, sortCode:String, amount: Double) {
        val account = getAccount(accountNumber, sortCode)
        account?.deposit(amount)
        println("Deposit successful!")
        println("£${"%.2f".format(amount)} deposited into account ${account?.accountNumber}")
        println("New Balance: ${formatPounds(account?.getBalance()!!)}")
    }

    fun withdraw (accountNumber: String, sortCode:String, amount: Double) {
        val account = getAccount(accountNumber, sortCode)
        account?.withdraw(amount)
        println("Withdrawal successful!")
        println("${formatPounds(amount)} withdrawn from account ${account?.accountNumber}")
        println("Please Wait...")
        println("New Balance: ${formatPounds(account?.getBalance()!!)}")
        println("Remember to collect your money!")
    }

    fun getBalance (accountNumber: String, sortCode:String): Double? {
        val account = getAccount(accountNumber, sortCode)
        val balance = account?.getBalance()
        println("Account Balance: ${formatPounds(balance!!)}")
        println("Would you like to perform another transaction?")

        return balance
    }

    private fun getAccount(accountNumber: String, sortCode: String): BankAccount? {
        require(accountNumber in accounts) { "Account not found." }
        val account = accounts[accountNumber]

        require(account?.sortCode?.replace("-", "") == sortCode.replace("-", "")) { "Credentials do not match." }
        return account
    }

    private fun formatPounds (amount: Double): String {
        return "£${String.format("%.2f", amount)}"
    }
}