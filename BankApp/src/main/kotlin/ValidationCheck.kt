package org.example

class ValidationCheck {
    companion object {
        private val accountNumberRegex = "^[89]\\d{7}$".toRegex()
        private val sortCodeRegex = "^4\\d-\\d{2}-\\d{2}$".toRegex()

        fun checkValid (accountNumber: String, sortCode: String): Boolean {
            require(accountNumber.matches(accountNumberRegex)) {
                "Account number should be 8 numbers, beginning with an 8 or a 9."
            }

            require(sortCode.matches(sortCodeRegex)) {
                "Sort code must be 6 numbers, formatted like 00-00-00."
            }

            return true
        }
    }
}