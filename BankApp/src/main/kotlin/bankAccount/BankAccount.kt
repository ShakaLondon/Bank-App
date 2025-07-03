package org.example.bankAccount

data class BankAccount(
    val accountNumber: String,
    val sortCode: String,
    val firstName: String,
    val lastName: String,
    private var balance: Double = 0.0
) {
    fun deposit(amount: Double) {
        require(amount > 0) { "Could not complete deposit. Deposit amount must be positive." }
        balance += amount
    }

    fun withdraw(amount: Double) {
        require(amount > 0) { "Could not complete withdrawal. Withdrawal amount must be positive." }
        require(amount <= balance ) { "Could not complete withdrawal. Insufficient funds." }
        balance -= amount
    }

    fun getBalance(): Double = balance

    operator fun get(accountNumber: String): String {
        return accountNumber
    }
}
