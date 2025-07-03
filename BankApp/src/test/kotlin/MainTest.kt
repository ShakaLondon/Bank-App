package org.example

import org.junit.jupiter.api.*
import java.io.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainAppTest {

    private val standardOut = System.out
    private val standardIn = System.`in`
    private lateinit var outputStreamCaptor: ByteArrayOutputStream

    @BeforeEach
    fun setUp() {
        outputStreamCaptor = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
        System.setIn(standardIn)
    }

    @Test
    fun `SHOULD create account and display success message`() {
        val input = """
            NewAccount Shakira Pingue
            Quit
        """.trimIndent()

        System.setIn(ByteArrayInputStream(input.toByteArray()))
        main()

        val output = outputStreamCaptor.toString()
        assertTrue(output.contains("Account created successfully!"))
        assertTrue(output.contains("Account Number: 80000000"))
        assertTrue(output.contains("Sort Code: 40-00-00"))
    }

    @Test
    fun `SHOULD reject account creation with lowercase first name`() {
        val input = """
            NewAccount shakira Pingue
            Quit
        """.trimIndent()

        System.setIn(ByteArrayInputStream(input.toByteArray()))
        main()

        val output = outputStreamCaptor.toString()
        assertTrue(output.contains("Error: First and last names must start with a capital letter"))
    }

    @Test
    fun `SHOULD deposit amount into account with valid credentials`() {
        val input = """
            NewAccount Shakira Pingue
            Deposit 80000000 40-00-00 50.00
            Quit
        """.trimIndent()

        System.setIn(ByteArrayInputStream(input.toByteArray()))
        main()

        val output = outputStreamCaptor.toString().trim()

        assertTrue(output.contains("Deposit successful!"))
        assertTrue(output.contains("£50.00 deposited into account 80000000"))
        assertTrue(output.contains("New Balance: £50.00"))
    }

    @Test
    fun `SHOULD NOT deposit amount into account with invalid sort code`() {
        val input = """
            NewAccount Shakira Pingue
            Deposit 80000000 40-00-11 50.00
            Quit
        """.trimIndent()

        System.setIn(ByteArrayInputStream(input.toByteArray()))
        main()

        val output = outputStreamCaptor.toString().trim()

        assertTrue(output.contains("Credentials do not match."))
    }

    @Test
    fun `SHOULD NOT deposit amount into account with invalid account number`() {
        val input = """
            NewAccount Shakira Pingue
            Deposit 80000011 40-00-00 50.00
            Quit
        """.trimIndent()

        System.setIn(ByteArrayInputStream(input.toByteArray()))
        main()

        val output = outputStreamCaptor.toString().trim()

        assertTrue(output.contains("Account not found."))
    }

    @Test
    fun `SHOULD withdraw amount from account with valid credentials and sufficient funds`() {
        val input = """
            NewAccount Shakira Pingue
            Deposit 80000000 40-00-00 50.00
            Withdraw 80000000 40-00-00 2.50
            Quit
        """.trimIndent()

        System.setIn(ByteArrayInputStream(input.toByteArray()))
        main()

        val output = outputStreamCaptor.toString().trim()

        assertTrue(output.contains("Withdrawal successful!"))
        assertTrue(output.contains("£2.50 withdrawn from account 80000000"))
        assertTrue(output.contains("New Balance: £47.50"))
        assertTrue(output.contains("Remember to collect your money!"))
    }

    @Test
    fun `SHOULD NOT withdraw amount from account with valid credentials and insufficient funds`() {
        val input = """
            NewAccount Shakira Pingue
            Withdraw 80000000 40-00-00 2.50
            Quit
        """.trimIndent()

        System.setIn(ByteArrayInputStream(input.toByteArray()))
        main()

        val output = outputStreamCaptor.toString().trim()

        assertTrue(output.contains("Could not complete withdrawal. Insufficient funds."))
    }

    @Test
    fun `SHOULD NOT withdraw negative amount from account with valid credentials and sufficient funds`() {
        val input = """
            NewAccount Shakira Pingue
            Deposit 80000000 40-00-00 50.00
            Withdraw 80000000 40-00-00 -2.50
            Quit
        """.trimIndent()

        System.setIn(ByteArrayInputStream(input.toByteArray()))
        main()

        val output = outputStreamCaptor.toString().trim()

        assertTrue(output.contains("Could not complete withdrawal. Withdrawal amount must be positive."))
    }

    @Test
    fun `SHOULD NOT withdraw amount from account with invalid sort code`() {
        val input = """
            NewAccount Shakira Pingue
            Deposit 80000000 40-00-00 50.00
            Withdraw 80000000 40-00-11 2.50
            Quit
        """.trimIndent()

        System.setIn(ByteArrayInputStream(input.toByteArray()))
        main()

        val output = outputStreamCaptor.toString().trim()

        assertTrue(output.contains("Credentials do not match."))
    }

    @Test
    fun `SHOULD NOT withdraw amount from account with invalid account number`() {
        val input = """
            NewAccount Shakira Pingue
            Deposit 80000000 40-00-00 50.00
            Withdraw 80000011 40-00-00 2.50
            Quit
        """.trimIndent()

        System.setIn(ByteArrayInputStream(input.toByteArray()))
        main()

        val output = outputStreamCaptor.toString().trim()

        assertTrue(output.contains("Account not found."))
    }

    @Test
    fun `SHOULD get account balance from account with valid credentials`() {
        val input = """
            NewAccount Shakira Pingue
            Balance 80000000 40-00-00
            Quit
        """.trimIndent()

        System.setIn(ByteArrayInputStream(input.toByteArray()))
        main()

        val output = outputStreamCaptor.toString().trim()

        assertTrue(output.contains("Account Balance: £0.00"))
        assertTrue(output.contains("Would you like to perform another transaction?"))
    }

    @Test
    fun `SHOULD get up to date account balance from account with valid credentials`() {
        val input = """
            NewAccount Shakira Pingue
            Deposit 80000000 40-00-00 50.00
            Balance 80000000 40-00-00
            Quit
        """.trimIndent()

        System.setIn(ByteArrayInputStream(input.toByteArray()))
        main()

        val output = outputStreamCaptor.toString().trim()

        assertTrue(output.contains("Account Balance: £50.00"))
        assertTrue(output.contains("Would you like to perform another transaction?"))
    }

    @Test
    fun `SHOULD NOT get account balance from account with invalid credentials`() {
        val input = """
            NewAccount Shakira Pingue
            Balance 80000011 40-00-00
            Quit
        """.trimIndent()

        System.setIn(ByteArrayInputStream(input.toByteArray()))
        main()

        val output = outputStreamCaptor.toString().trim()

        assertTrue(output.contains("Account not found."))
    }
}
