package com.hotmail.or_dvir.mydoc.models

import java.util.UUID

data class Doctor(
    val id: UUID,
    val name: String,
    val speciality: String?,
    val address: SimpleAddress?,
    val contactDetails: ContactDetails?
)

//todo
// opening times
//      complex!!! add option for a break in the middle of the day

object DoctorFactory
{
    fun createEmpty() = Doctor(
        UUID.randomUUID(),
        "",
        null,
        AddressFactory.createEmpty(),
        ContactDetailsFactory.createEmpty()
    )
}