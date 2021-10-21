package com.hotmail.or_dvir.mydoc.models

import android.content.Context
import com.hotmail.or_dvir.mydoc.R

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
    fun getBasicAddress(): String
    {
        //todo format the address according to locale!!!
        return StringBuilder().apply {
            append("$street $houseNumber, ")
            postCode?.let { append("$it ") }
            append(city)
            country?.let { append(", $it") }
        }.toString()
    }

    fun getDetailedAddress(context: Context): String?
    {
        //todo present in a nicer way? handle "1st, 2nd, 3rd..."
        val details = StringBuilder().apply {
            //todo find a better way!
            // this class should NOT be dependent on R file!!!
            // this class should NOT be dependent on context!!!
            apartmentNumber?.let { append(context.getString(R.string.apartment_s, it)) }
            floor?.let { append(", ${context.getString(R.string.floor_d, it)}") }
        }.toString()

        return details.takeIf { it.isNotEmpty() }
    }
}

object AddressFactory
{
    //todo remove when no longer needed
    fun getDummyAddress() = Address(
        "escher str.",
        "84d",
        "pulheim",
        50259,
        "Germany",
        "5",
        1
    )
}