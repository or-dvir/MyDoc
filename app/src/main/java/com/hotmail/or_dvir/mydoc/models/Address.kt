package com.hotmail.or_dvir.mydoc.models

import androidx.room.ColumnInfo

//todo addresses are complicated!!!!
// "best" solution is probably google places but it requires a billing account.
// for now use SimpleAddress which is just free text...
//data class Address(
//    val street: String,
//    val houseNumber: String,
//    val city: String,
//    val postCode: Int? = null,
//    //todo should country be mandatory?
//    // if so, either get it from locale by default, or add it in preferences
//    val country: String? = null,
//    val apartmentNumber: String? = null,
//    val floor: Int? = null
////todo add state
////todo add free text note (e.g. left to the pharmacy)
//)
//{
//    /**
//     * returns the address as a string containing street, house number, postcode (if available),
//     * city, country (if available.
//     * Example 1: Main Street 52, 12345 Los Angeles, United States
//     */
//    fun getShortAddress(): String
//    {
//        //todo format the address according to locale!!!
//        return StringBuilder().apply {
//            append("$street $houseNumber, ")
//            postCode?.let { append("$it ") }
//            append(city)
//            country?.let { append(", $it") }
//        }.toString()
//    }
//
//    fun getAddressDetails(context: Context): String?
//    {
//        //todo present in a nicer way? handle "1st, 2nd, 3rd..."
//        val details = StringBuilder().apply {
//            //todo find a better way!
//            // this class should NOT be dependent on R file!!!
//            // this class should NOT be dependent on context!!!
//            apartmentNumber?.let { append(context.getString(R.string.apartment_s, it)) }
//            floor?.let { append(", ${context.getString(R.string.floor_d, it)}") }
//        }.toString()
//
//        return details.takeIf { it.isNotEmpty() }
//    }
//}

data class SimpleAddress(
    //IMPORTANT NOTE!!!
    // this class is used with @Embedded annotation
    // DO NOT CHANGE FIELD NAMES!!!
    @ColumnInfo(name = COLUMN_ADDRESS_LINE)
    val addressLine: String?,
    @ColumnInfo(name = COLUMN_NOTE)
    val note: String?
)
{
//    check all combinations fcor to see what database looks like
//    make sure given column names are used
//    if columns are correct, remove note in constructor

    //todo all fields are nullable for initial convenience.
    // should i make some mandatory?

    companion object
    {
        const val COLUMN_ADDRESS_LINE = "_addressLine"
        const val COLUMN_NOTE = "_addressNote"
    }

//    //todo can this be private?
//    fun isEmpty() = addressLine.isBlank() && note.isNullOrBlank()
//    fun takeIfNotEmpty() = if (isEmpty()) null else this
}

object AddressFactory
{
    fun createEmptyAddress() = SimpleAddress(null, null)
}