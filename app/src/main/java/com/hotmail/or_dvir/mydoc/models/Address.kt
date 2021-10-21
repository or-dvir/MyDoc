package com.hotmail.or_dvir.mydoc.models

data class Address(
    val street: String,
    val houseNumber: String,
    val city: String,
    val postCode: Int? = null,
    //todo should country be mandatory?
    // if so, either get it from locale by default, or add it in preferences
    val country: String? = null,
    val apartmentNumber: String? = null,
    val floor: Int? = null
)
{
    fun format(): String
    {
        //todo format the address according to locale!!!
        return StringBuilder().apply {
            append("$street $houseNumber, ")
            postCode?.let { append("$it ") }
            append(city)
            country?.let { append(", $it") }
        }.toString()
    }
}

object AddressFactory
{
    //todo remove when no longer needed
    fun getDummyAddress() = Address("escher str.", "84d", "pulheim")
}