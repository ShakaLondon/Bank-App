package bank

import org.example.bank.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BankTest {
    @Test
    fun `createAccount SHOULD generate unique account information with valid credentials`() {
        val bank = Bank("Test Bank")
        val account1 = bank.createAccount("Shakira", "Pingue")
        val account2 = bank.createAccount("Shakira", "Pingue")

        assertNotNull(account1)
        assertTrue(account1.accountNumber.matches(Regex("^[89]\\d{7}$")))
        assertTrue(account1.sortCode.matches(Regex("^4\\d-\\d{2}-\\d{2}$")))

        assertNotNull(account2)
        assertTrue(account2.accountNumber.matches(Regex("^[89]\\d{7}$")))
        assertTrue(account2.sortCode.matches(Regex("^4\\d-\\d{2}-\\d{2}$")))

        assertTrue(account1.accountNumber != account2.accountNumber)
        assertTrue(account1.sortCode != account2.sortCode)
    }

    @Test
    fun `deposit SHOULD increase account balance by specified amount`() {
        val bank = Bank("Test Bank")
        val account = bank.createAccount("Shakira", "Pingue")

        bank.deposit(account.accountNumber, account.sortCode, 100.0)
        assertEquals(100.0, bank.getBalance(account.accountNumber, account.sortCode))
    }

    @Test
    fun `withdraw SHOULD decrease account balance by specified amount`() {
        val bank = Bank("Test Bank")
        val account = bank.createAccount("Shakira", "Pingue")

        bank.deposit(account.accountNumber, account.sortCode, 200.0)
        assertEquals(200.0, bank.getBalance(account.accountNumber, account.sortCode))

        bank.withdraw(account.accountNumber, account.sortCode, 50.0)

        assertEquals(150.0, bank.getBalance(account.accountNumber, account.sortCode))
    }

    @Test
    fun `getBalance SHOULD return correct amount`() {
        val bank = Bank("Test Bank")
        val account = bank.createAccount("Shakira", "Pingue")

        bank.deposit(account.accountNumber, account.sortCode, 75.0)

        assertEquals(75.0, bank.getBalance(account.accountNumber, account.sortCode))
    }

    @Test
    fun `withdraw SHOULD throw error on insufficient funds`() {
        val bank = Bank("Test Bank")
        val account = bank.createAccount("Shakira", "Pingue")

        bank.deposit(account.accountNumber, account.sortCode, 20.0)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            bank.withdraw(account.accountNumber, account.sortCode, 50.0)
        }

        assertEquals("Could not complete withdrawal. Insufficient funds.", exception.message)
    }

    @Test
    fun `deposit SHOULD throw error for wrong sort code`() {
        val bank = Bank("Test Bank")
        val account = bank.createAccount("Shakira", "Pingue")

        val exception = assertThrows(IllegalArgumentException::class.java) {
            bank.deposit(account.accountNumber, "41-00-00", 50.0)
        }

        assertEquals("Credentials do not match.", exception.message)
    }

    @Test
    fun `getBalance SHOULD throw error for account that does not exist`() {
        val bank = Bank("Test Bank")

        val exception = assertThrows(IllegalArgumentException::class.java) {
            bank.getBalance("89999999", "40-00-00")
        }

        assertEquals("Account not found.", exception.message)
    }

    @Test
    fun `getBalance SHOULD throw error when the sort code does not match`() {
        val bank = Bank("Test Bank")

        val account = bank.createAccount("Shakira", "Pingue")

        val exception = assertThrows(IllegalArgumentException::class.java) {
            bank.withdraw(account.accountNumber, "40-00-01", 45.00)
        }

        assertEquals("Credentials do not match.", exception.message)
    }
}