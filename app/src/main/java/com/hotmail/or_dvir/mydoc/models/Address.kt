package com.hotmail.or_dvir.mydoc.models

data class Address(
    val street: String = "",
    val houseNumber: String = "",
    val apartmentNumber: String = "",
    val floor: Int = DEFAULT_INT,
    val postCode: Int = DEFAULT_INT,
    val city: String = "",
    val country: String = ""
)
{
    private companion object
    {
        private const val DEFAULT_INT = -10000
    }

    fun formatAddress(): String {
        val builder = StringBuilder()

        finish me.
                handle case where no information is available

        return builder.toString()
    }
}