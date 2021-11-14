package com.hotmail.or_dvir.mydoc.models

import java.util.UUID

data class Doctor(
    val id: UUID,
    val name: String,
    val speciality: String?,
    val address: SimpleAddress?,
    val contactDetails: ContactDetails?,
    val openingTimes: List<OpeningTime>?
)

//todo
// opening times
//      day of week
//          list of "from-to" hours
//      complex!!! add option for a break in the middle of the day

object DoctorFactory
{
    fun createDefault() = Doctor(
        UUID.randomUUID(),
        "",
        null,
        AddressFactory.createDefault(),
        ContactDetailsFactory.createDefault(),
        null
    )
}