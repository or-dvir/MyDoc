package com.hotmail.or_dvir.mydoc.models

import java.util.UUID

data class Doctor(
    val id: UUID,
    val name: String,
    val speciality: String?,
    val address: SimpleAddress?
)

//todo
// opening times
// contact details
//      phone (format with https://developer.android.com/reference/android/telephony/PhoneNumberUtils.html)
//      email
// link to website

object DoctorFactory
{
    fun createEmptyDoctor() = Doctor(
        UUID.randomUUID(),
        "",
        null,
        AddressFactory.createEmptyAddress()
    )
}