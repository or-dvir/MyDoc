package com.hotmail.or_dvir.mydoc.models

data class Address(
    val street: String,
    val houseNumber: String,
    val city: String,
    val apartmentNumber: String? = null,
    val floor: Int? = null,
    val postCode: Int? = null,
    //todo should country be mandatory?
    // if so, either get it from locale by default, or add it in preferences
    val country: String? = null
)
{
    fun formatAddress(): String
    {
        val builder = StringBuilder().apply {
            append("$street, ")
            append(street)

        }

//        finish me .
//        handle case where no information is available

        return builder.toString()
    }
}

object AddressFactory {
    fun getDummyAddress() = Address("escher str.", "84d", "pulheim")
}