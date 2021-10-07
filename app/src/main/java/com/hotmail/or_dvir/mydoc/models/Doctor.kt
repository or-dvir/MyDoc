package com.hotmail.or_dvir.mydoc.models

import java.util.UUID

data class Doctor(
    val id: UUID,
    val name: String,
    val specialty: String? = null,
    val address: Address? = null
)

add address to
    doctor details (open maps!)
    new/edit doctor

//todo
// opening times
// contact details
//      phone (format with https://developer.android.com/reference/android/telephony/PhoneNumberUtils.html)
//      email
// link to website

object DoctorFactory
{
    fun getDummyDoctor() = Doctor(UUID.randomUUID(), "Dr. Evil")
}