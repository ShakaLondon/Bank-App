package bankAccount

import org.example.bankAccount.BankAccount
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BankAccountTest {
    @Test
    fun `getBalance SHOULD return account balance`() {
        val account = BankAccount("80000000", "40-00-00", "Shakira", "Pingue")

        account.deposit(98.67)
        assertEquals(account.getBalance(), 98.67)
    }

    @Test
    fun `deposit SHOULD add the requested amount to account balance`() {
        val account = BankAccount("80000000", "40-00-00", "Shakira", "Pingue")

        account.deposit(65.00)

        assertEquals(account.getBalance(), 65.00)
    }

    @Test
    fun `deposit should NOT add the requested amount to account balance WHEN amount is negative AND should throw Exception`() {
        val account = BankAccount("80000000", "40-00-00", "Shakira", "Pingue")

        val exception = assertThrows(IllegalArgumentException::class.java) {
            account.deposit(-5.00)
        }

        assertEquals("Could not complete deposit. Deposit amount must be positive.", exception.message)
    }

    @Test
    fun `withdraw SHOULD add the requested amount to account balance`() {
        val account = BankAccount("80000000", "40-00-00", "Shakira", "Pingue")

        account.deposit(65.00)
        assertEquals(account.getBalance(), 65.00)

        account.withdraw(5.40)
        assertEquals(account.getBalance(), 59.60)
    }

    @Test
    fun `withdraw should NOT add the requested amount to account balance WHEN amount is less than current balance AND should throw Exception`() {
        val account = BankAccount("80000000", "40-00-00", "Shakira", "Pingue")

        account.deposit(98.67)
        assertEquals(account.getBalance(), 98.67)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            account.withdraw(100.87)
        }

        assertEquals("Could not complete withdrawal. Insufficient funds.", exception.message)
    }

    @Test
    fun `withdraw should NOT add the requested amount to account balance WHEN amount is negative AND should throw Exception`() {
        val account = BankAccount("80000000", "40-00-00", "Shakira", "Pingue")

        account.deposit(98.67)
        assertEquals(account.getBalance(), 98.67)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            account.withdraw(-0.87)
        }

        assertEquals("Could not complete withdrawal. Withdrawal amount must be positive.", exception.message)
    }
}